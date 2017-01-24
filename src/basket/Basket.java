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
            System.out.println("Conectado a la bbdd jugadores");
            Equipo  e  = new Equipo("BarcelonaFC", "Barcelona",LocalDate.of(1902,06,06));
            Jugador j = new Jugador("Larry", LocalDate.of(1990,02,27),34,"Base",5,12,e);
//           gestor.insertEquipo(e);
//            gestor.insertJugador(j);
            System.out.println("jugador dado de alta.");
            List<Jugador> todosJugadores = gestor.selectAllJugadores();
            System.out.println("Listado de jugadores");
            for (Jugador c : todosJugadores) {
                System.out.println(c);
            }

            List<Equipo> todosEquipos = gestor.selectAllEquipos();
            System.out.println("Listado de equipos");
            for (Equipo c : todosEquipos) {
                System.out.println(c);
            }
            gestor.desconectar();
            System.out.println("Cerrada la conexión con la bbdd.");
        } catch (SQLException ex) {
            System.out.println("Error con la BBDD: "+ex.getMessage());
        }



    }
    }

