package Buscaminas;

import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;



public class BuscaminasFX extends Application {

    //----------------------------------------------
    // Variables de interfaz
    //----------------------------------------------
    
    //Buttons
    Button btnReinicio;
    private Button[][] matrizBotones;
    
    //Labels
    Label lblEstado;
    
    //Layouts
    BorderPane panelRaiz;
    HBox panelEstado;
    Region espaciador;
    GridPane tablero;
    
    //----------------------------------------------
    // Variables del juego
    //----------------------------------------------
    private boolean[][] minas;
    private boolean[][] revelado;
    private boolean[][] bandera;

    private final int TAM = 5;
    private final int NUM_MINAS = 5;
    
    private int casillasAbiertas = 0;


    //----------------------------------------------
    //               INICIO
    //----------------------------------------------
    
    public static void main (String[] args){
        
        launch(args);
    }
    
    @Override
    public void start (Stage ventana){
        
        //Inicio de parametros de juegp
        
        minas = new boolean[TAM][TAM];
        revelado = new boolean[TAM][TAM];
        bandera = new boolean[TAM][TAM];
        
        inicializarMinas();
        
        /* Este bucle muestra las posiciones de las minas por consola, útil para hacer pruebas
        for(int i=0;i<TAM;i++){
            for(int j=0;j<TAM;j++){
                if(minas[i][j]){
                    System.out.printf("[%d][%d]\n", i,j);
                }
            }
        }*/

        //Creamos la raiz para montar la escena con dos layouts
        panelRaiz = new BorderPane();
        
        //Creamos el panel superior de informaciónm y reinicio
        panelEstado = new HBox(15);
        
        //Creamos la etiqueta de estado del juego
        lblEstado = new Label("Estado: Jugando...");
        lblEstado.getStyleClass().add("estado");
        
        //Creamos el boton de reinicio
        btnReinicio = new Button("Reiniciar");
        btnReinicio.setOnMouseClicked(click ->{
            reiniciarJuego();    
        });
        
        espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);
        panelEstado.setAlignment(Pos.TOP_CENTER);
        panelEstado.setPadding(new Insets(5));
        panelEstado.getChildren().addAll(lblEstado,espaciador,btnReinicio);
        
        //Creamos el panel donde se desarrolla el juego e inicializamos la matriz para guardar los botones.
        tablero = new GridPane();
        tablero.getStyleClass().add("tablero");
        matrizBotones = new Button[TAM][TAM];
        
        //Rellenamos el grid con botones para detectar los eventos asigandoles tamaño y comportamiento en el momento de instanciación 
        for(int i=0;i<TAM;i++){
            for(int j=0;j<TAM;j++){
                
                int fil=i,col=j;
                
                Button btnCasilla = new Button();
                btnCasilla.setPrefSize(80, 80);
                btnCasilla.getStyleClass().add("casilla");
                
                btnCasilla.setOnMouseClicked(click ->{
                    if(revelado[fil][col]){
                        return;
                    }
                    
                    //Diferenciacion de clicks y sus acciones
                    if(click.getButton() == MouseButton.PRIMARY){
                        descubrir(fil, col);
                    }else if(click.getButton() == MouseButton.SECONDARY){
                        ponerBandera(fil, col);
                    }
                
                });
                
                matrizBotones[fil][col] = btnCasilla;
                tablero.add(btnCasilla, col, fil);
            }
        }
        //tablero.setGridLinesVisible(true);
        tablero.setAlignment(Pos.CENTER);
        tablero.setHgap(2);
        tablero.setVgap(2);
        
        //Terminamos de montar el panel raiz
        panelRaiz.setTop(panelEstado);
        panelRaiz.setCenter(tablero);
        panelRaiz.setPadding(new Insets(10));
        
        //Crear escena y aplicar estilo
        Scene escena = new Scene(panelRaiz, 500,550);
        try{
            escena.getStylesheets().add(getClass().getResource("Buscaminas.css").toExternalForm());
        }catch(NullPointerException ex){
            System.err.printf("Hoja de estilo CSS no encontrada. Detalles:\n%s\n", ex.getMessage());
        }
        
        //Titulo y tamaño fijo
        ventana.setTitle("Buscaminas"); //Queria ponerle Buscaminas naval por seguir la temática pero en la tarea especifica que debe ser solo Buscaminas
        ventana.setResizable(false);
        
        //Añadir imagen a la ventana
        try{
            Image logo = new Image(getClass().getResourceAsStream("logoBuscaminas.png"));
            ventana.getIcons().add(logo);
        }catch(NullPointerException | IllegalArgumentException ex){
            System.err.printf("Imagen del logo no encontrada. Detalles:\n%s\n", ex.getMessage());
        }
        
        //Añadir ecena a escenario y mostrarlo
        ventana.setScene(escena);
        ventana.show();
    }
    
    //----------------------------------------------
    // Colocar minas aleatorias
    //----------------------------------------------

    /**
     * Coloca las minas en posiciones aleatorias dentro del tablero.
     * 
     * Se generan coordenadas aleatorias hasta alcanzar el número total de minas
     * definido en la constante NUM_MINAS.
     */
    private void inicializarMinas(){

        Random r = new Random();

        int minasColocadas = 0;

        while(minasColocadas < NUM_MINAS){

            int f = r.nextInt(TAM);
            int c = r.nextInt(TAM);

            if(!minas[f][c]){
                minas[f][c] = true;
                minasColocadas++;
            }
        }
    }

    //----------------------------------------------
    // Click izquierdo
    //----------------------------------------------

    /**
     * Descubre una casilla del tablero.
     * 
     * Si la casilla contiene una mina, el jugador pierde la partida y se muestran
     * todas las minas. Si no contiene mina, se muestra el número de minas
     * adyacentes.
     * 
     * También se controla la condición de victoria cuando todas las casillas
     * seguras han sido descubiertas.
     * 
     * @param fila Fila de la casilla seleccionada.
     * @param col Columna de la casilla seleccionada.
     */
    private void descubrir(int fila,int col){
        //Bloqueamos las casillas marcadas con banderas
        if(bandera[fila][col]){
            return;
        }
        
        revelado[fila][col] = true;
        matrizBotones[fila][col].setDisable(true);
        
        //Mina?
        if(minas[fila][col]){
            //Mina
            mostrarMinas();
            for(int i=0;i<TAM;i++){
                for(int j=0;j<TAM;j++){
                    matrizBotones[i][j].setDisable(true);
                }
            }
            lblEstado.setText("Estado: Camino hacia Fondo de Bikini...  Otra?  ----> ");
            
        }else{
            //Seguro
            int minasCerca = contarMinas(fila,col);
            casillasAbiertas++;
            matrizBotones[fila][col].getStyleClass().add("abierta");
            
            if(minasCerca > 0){
                matrizBotones[fila][col].setText(String.valueOf(minasCerca));
            }
            
        }
        
        //Victoria
        if(casillasAbiertas== (TAM * TAM - NUM_MINAS)){
            lblEstado.setText("Estado: Victoria!...  Otra?  ---->");
            mostrarMinas();
        }
    }


    //----------------------------------------------
    // Click derecho (banderas)
    //----------------------------------------------

    /**
     * Coloca o elimina una bandera en la casilla seleccionada.
     * 
     * Las banderas permiten al jugador marcar posibles ubicaciones de minas.
     * No se permite colocar banderas en casillas que ya han sido reveladas.
     * 
     * @param fila Fila de la casilla seleccionada.
     * @param col Columna de la casilla seleccionada.
     */
    private void ponerBandera(int fila,int col){
        
        bandera[fila][col] = !bandera[fila][col];
        
        if(!bandera[fila][col]){
            matrizBotones[fila][col].setGraphic(null);
        } else{
            try{
                Image imgBandera = new Image(getClass().getResourceAsStream("bandera.png"));
                ImageView viewBandera = new ImageView(imgBandera);
                
                //Limitamos el tamaño para que no distorsione el grid y mantenga la proporción
                viewBandera.setFitHeight(40);
                viewBandera.setFitWidth(40);
                viewBandera.setPreserveRatio(true);
                
                matrizBotones[fila][col].setGraphic(viewBandera);
                
            }catch(NullPointerException | IllegalArgumentException ex){
                System.err.printf("Imagen para bandera no encontrada. Detalles:\n%s\n", ex.getMessage());
            }
        }
        
    }

    //----------------------------------------------
    // Contar minas alrededor
    //----------------------------------------------

    /**
     * Cuenta el número de minas adyacentes a una casilla determinada.
     * 
     * Se recorren las ocho posiciones vecinas alrededor de la casilla indicada
     * comprobando si contienen minas.
     * 
     * @param f Fila de la casilla.
     * @param c Columna de la casilla.
     * @return Número de minas alrededor de la casilla.
     */
    private int contarMinas(int f,int c){

        int contador = 0;

        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){

                int nf = f+i;
                int nc = c+j;

                if(nf>=0 && nf<TAM && nc>=0 && nc<TAM){

                    if(minas[nf][nc]){
                        contador++;
                    }

                }
            }
        }

        return contador;
    }

    //----------------------------------------------
    // Mostrar todas las minas
    //----------------------------------------------

    /**
     * Revela todas las minas del tablero.
     * 
     * Este método se utiliza cuando el jugador pierde o cuando gana la partida,
     * mostrando la posición de todas las minas existentes.
     */
    private void mostrarMinas(){
        
        //Cargamos la imagen de la bomba
        try{
            Image imgBomba = new Image(getClass().getResourceAsStream("bomba.png")); //Hubiese quedado mejor una mina naval, pero esta imagen ya viene en el proyecto base
                                                                                            // por lo que no la modificaré
            //Las revelamos todas
            for(int i=0;i<TAM;i++){
                for(int j=0;j<TAM;j++){
                    if(minas[i][j]){
                        
                        ImageView viewBomba = new ImageView(imgBomba);
            
                        //Limitamos el tamaño para que no distorsione el grid y mantenga la proporción
                        viewBomba.setFitHeight(50);
                        viewBomba.setFitWidth(50);
                        viewBomba.setPreserveRatio(true);
                        
                        matrizBotones[i][j].setGraphic(viewBomba);
                        matrizBotones[i][j].setDisable(true);
                        matrizBotones[i][j].getStyleClass().add("abierta");
                    }
                }
            }
            
        }catch(NullPointerException | IllegalArgumentException ex){
            System.err.printf("Imagen para Bomba no encontrada. Detalles:\n%s\n", ex.getMessage());
        }
        
    }
    
    /**
     * Reinicia la partida actual.
     * 
     * Se restablecen todas las estructuras de datos del juego, se limpian los
     * botones del tablero y se vuelven a colocar las minas en posiciones
     * aleatorias.
     */
    private void reiniciarJuego(){
        
        //Limpiamos el tablero
        for(int i=0;i<TAM;i++){
            for(int j=0;j<TAM;j++){
                matrizBotones[i][j].setText("");
                matrizBotones[i][j].setGraphic(null);
                matrizBotones[i][j].setDisable(false);
                matrizBotones[i][j].getStyleClass().remove("abierta");
            }
        }
        
        //Que el recolector de basura se encarge de las matrices viejas sin referencia
        minas = new boolean[TAM][TAM];
        revelado = new boolean[TAM][TAM];
        bandera = new boolean[TAM][TAM];
        
        setCasillasAbiertas(0);
        lblEstado.setText("Estado: Jugando...");
        inicializarMinas();
        
    }
    
    /**
     * Setter para el número de casillas abiertas.
     * 
     * Establece el número de casillas abiertas al número dado.
     * 
     * @param numAbiertas Número de casillas abiertas a establecer.
     */
    private void setCasillasAbiertas(int numAbiertas){
        this.casillasAbiertas = numAbiertas;
    }
}