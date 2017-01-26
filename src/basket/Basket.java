package basket;

import model.Equipo;
import model.Jugador;
import persistence.BasketJDBC;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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

            //
            System.out.println("Modificar skills jugador");
            gestor.modificarJugador(j, 5, 5,5);

            /////

            System.out.println("Modificar team jugador:");
            gestor.modificarEquipoJugador(j, e);


            //////
            Jugador c = new Jugador("borrar", LocalDate.of(1902,02,24),45,"En Paro",4,12,e);
            gestor.insertJugador(c);
            gestor.eliminarJugador(c);

            //

            Jugador jugador = gestor.getJugador("Larry");

            //

            System.out.println("Busqueda jugador : "+gestor.getJugadorContaining("Larr"));

            //

            System.out.println("Busqueda jugadores mas canastas de 4: "+gestor.getJugadoresMasCanastasQue(3));

            ///

            System.out.println("Busqueda jugadores canastas entre 1 y 9: "+gestor.getjugadoresCanastasEntre(1, 100));

            //

            System.out.println("posicion base "+gestor.getJugadoresPosicion("base"));

            //

            System.out.println("nacidos antes que: "+gestor.getJugadoresNacidosAntesQue(LocalDate.of(1903,03,25)));
            //

            System.out.println("avg jugadores : "+gestor.getResumenStatsGroupByPosicion());

            //

            System.out.println("Equipos por localidad: "+gestor.getEquiposPorLocalidad("Barcelona"));

             //

            System.out.println("Equipo: "+gestor.getJugadoresEquipo("Real Madrid"));

            //

            System.out.println("Jugadores de un equipo misma posicion: "+gestor.getJugadoresEquipoPos("Larry", "Base"));



            gestor.desconectar();
            System.out.println("Cerrada la conexión con la bbdd.");
        } catch (SQLException ex) {
            System.out.println("Error con la BBDD: "+ex.getMessage());
        }



    }

    }

