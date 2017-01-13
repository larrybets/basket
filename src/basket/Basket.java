package basket;

import model.Equipo;
import model.Jugador;
import persistence.BasketJDBC;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by DAM on 13/1/17.
 */
public class Basket {

    public static void main(String[] args) {

        BasketJDBC gestor = new BasketJDBC();

        try {
            System.out.println("Estableciendo conexión con la bbdd...");
            gestor.conectar();
            System.out.println("Conectado a la bbdd restaurant");
            Equipo  e  = new Equipo("Real Madrid", "Madrid",LocalDate.of(1902,06,06));
            Jugador j = new Jugador("Larry Bird", LocalDate.of(1990,02,27),34,"Base",5,12,e);
            gestor.insertJugador(j);
            System.out.println("Cocinero dado de alta.");
            List<Jugador> todosCocineros = gestor.selectAllJugadores();
            System.out.println("Listado de cocineros");
            for (Jugador c : todosCocineros) {
                System.out.println(c);
            }
            gestor.desconectar();
            System.out.println("Cerrada la conexión con la bbdd.");
        } catch (SQLException ex) {
            System.out.println("Error con la BBDD: "+ex.getMessage());
        }



    }
    }

