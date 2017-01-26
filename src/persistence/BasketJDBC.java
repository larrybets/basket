package persistence;

import model.Equipo;
import model.Estadisticas;
import model.Jugador;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAM on 13/1/17.
 */
public class BasketJDBC {

    private Connection conexion;


    public void insertEquipo(Equipo e) throws SQLException {
        String insert = "insert into team values (?, ?, ?);";
        PreparedStatement ps = conexion.prepareStatement(insert);
        // Vamos dando valores a los interrogantes
        ps.setString(1, e.getNombre());
        ps.setString(2, e.getLocalidad());
        ps.setDate(3, java.sql.Date.valueOf(e.getFechaCreacion()));

        // ejecutamos la consultas
        ps.executeUpdate();
        ps.close();
    }

    public List<Equipo> selectAllEquipos() throws SQLException {
        List<Equipo> equipos = new ArrayList<>();
        String query = "select * from team ";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Equipo e = new Equipo();
            e.setNombre(rs.getString("name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));


            //   j.setEquipo(rs.getString());

            equipos.add(e);
        }
        rs.close();
        st.close();
        return equipos;
    }

    public List<Jugador> selectAllJugadores() throws SQLException {
        List<Jugador> jugadores = new ArrayList<>();
        String query = "select * from player ";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("name"));
            j.setAsistencias(rs.getInt("nassists"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
            j.setCanastas(rs.getInt("nbaskets"));
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
            j.setEquipo(new Equipo(rs.getString("team")));

            jugadores.add(j);
        }
        rs.close();
        st.close();
        return jugadores;
    }

    public void insertJugador(Jugador j) throws SQLException {
        String insert = "insert into player values (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conexion.prepareStatement(insert);
        // Vamos dando valores a los interrogantes
        ps.setString(1, j.getNombre());
        ps.setDate(2, java.sql.Date.valueOf(j.getNacimiento()));
        ps.setString(6, j.getPosicion());
        ps.setInt(4, j.getAsistencias());
        ps.setInt(3, j.getCanastas());
        ps.setInt(5, j.getRebotes());
        ps.setString(7, j.getEquipo().getNombre());
        // ejecutamos la consultas
        ps.executeUpdate();
        ps.close();
    }

    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/basket";
        String usr = "root";
        String pass = "";
        conexion = DriverManager.getConnection(url, usr, pass);
    }

    // Función que desconecta de la bbdd
    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

public void modificarJugador(Jugador j,int canastas, int asistencias, int rebotes)throws  SQLException{
    String modificar = "update player set nbaskets = ?, nassists = ?, nrebounds = ?  where name = ?";
    PreparedStatement ps = conexion.prepareStatement(modificar);
    ps.setInt(1,canastas);
    ps.setInt(2,asistencias);
    ps.setInt(3,rebotes);
    ps.setString(4, j.getNombre());
    ps.executeUpdate();
    ps.close();
}

    public void modificarEquipoJugador(Jugador j, Equipo e) throws SQLException {
        String update = "update player set team = ? where name = ?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setString(1, e.getNombre());
        ps.setString(2, j.getNombre());
        ps.executeUpdate();
        ps.close();
    }
    public void eliminarJugador(Jugador j) throws SQLException {
        String borrar = "delete from player where name = ?";
        PreparedStatement ps = conexion.prepareStatement(borrar);
        ps.setString(1, j.getNombre());
        ps.executeUpdate();
        ps.close();
    }


    public Jugador getJugador(String nombre) throws SQLException {
        String buscar = "select * from player e, team t where e.name = ? and e.team=t.name";
        PreparedStatement ps = conexion.prepareStatement(buscar);
        ps.setString(1, nombre);
        Jugador j = new Jugador();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            j.setNombre(rs.getString("e.name"));
            j.setAsistencias(rs.getInt("nassists"));
            j.setCanastas(rs.getInt("nbaskets"));
            Equipo e = new Equipo();
            e.setNombre(rs.getString("t.name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));
            j.setEquipo(e);
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
        }
        rs.close();
        return j;
    }

    public List<Jugador> getJugadorContaining(String nombre) throws SQLException {
        nombre += "%";
        String query = "select * from player e, team t where e.name like ? and e.team=t.name";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();
        List<Jugador> lista = new ArrayList<>();
        while (rs.next()) {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("e.name"));
            j.setAsistencias(rs.getInt("nassists"));
            j.setCanastas(rs.getInt("nbaskets"));
            Equipo e = new Equipo();
            e.setNombre(rs.getString("t.name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));
            j.setEquipo(e);
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
            lista.add(j);
        }
        rs.close();
        return lista;
    }

    public List<Jugador> getJugadoresMasCanastasQue(int canastas) throws SQLException {
        String query = "select * from player e, team t where nbaskets >= ? and e.team=t.name";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setInt(1, canastas);
        ResultSet rs = ps.executeQuery();
        List<Jugador> lista = new ArrayList<>();
        while (rs.next()) {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("e.name"));
            j.setAsistencias(rs.getInt("nassists"));
            j.setCanastas(rs.getInt("nbaskets"));
            Equipo e = new Equipo();
            e.setNombre(rs.getString("t.name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));
            j.setEquipo(e);
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
            lista.add(j);
        }
        rs.close();
        return lista;
    }

    public List<Jugador> getjugadoresCanastasEntre(int menos, int mas) throws SQLException {
        String query = "select * from player e, team t where nbaskets >= ? and nbaskets <= ? and e.team=t.name";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setInt(1, menos);
        ps.setInt(2, mas);
        ResultSet rs = ps.executeQuery();
        List<Jugador> lista = new ArrayList<>();
        while (rs.next()) {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("e.name"));
            j.setAsistencias(rs.getInt("nassists"));
            j.setCanastas(rs.getInt("nbaskets"));
            Equipo e = new Equipo();
            e.setNombre(rs.getString("t.name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));
            j.setEquipo(e);
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
            lista.add(j);
        }
        rs.close();
        return lista;
    }

    public List<Jugador> getJugadoresPosicion(String posicion) throws SQLException {
        String query = "select * from player e, team t where position = ? and e.team=t.name";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setString(1, posicion);
        ResultSet rs = ps.executeQuery();
        List<Jugador> lista = new ArrayList<>();
        while (rs.next()) {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("e.name"));
            j.setAsistencias(rs.getInt("nassists"));
            j.setCanastas(rs.getInt("nbaskets"));
            Equipo e = new Equipo();
            e.setNombre(rs.getString("t.name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));
            j.setEquipo(e);
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
            lista.add(j);
        }
        rs.close();
        return lista;
    }
    public List<Jugador> getJugadoresNacidosAntesQue(LocalDate nacimiento) throws SQLException {
        String query = "select * from player e, team t where e.team=t.name and birth < ?";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setDate(1, java.sql.Date.valueOf(nacimiento));
        ResultSet rs = ps.executeQuery();
        List<Jugador> lista = new ArrayList<>();
        while (rs.next()) {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("e.name"));
            j.setAsistencias(rs.getInt("nassists"));
            j.setCanastas(rs.getInt("nbaskets"));
            Equipo e = new Equipo();
            e.setNombre(rs.getString("t.name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));
            j.setEquipo(e);
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
            lista.add(j);
        }
        rs.close();
        return lista;
    }

    public Object getResumenStatsGroupByPosicion() throws SQLException {
        String query = "select avg(nbaskets), avg(nassists), avg(nrebounds),max(nbaskets), max(nassists), " +
                "max(nrebounds),min(nbaskets), min(nassists), min(nrebounds) from player group by position;";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        Estadisticas s = new Estadisticas();
        while (rs.next()) {
            s.avgBaskets = rs.getInt("avg(nbaskets)");
            s.avgAssists = rs.getInt("avg(nassists)");
            s.avgRebounds = rs.getInt("avg(nrebounds)");
            s.minBaskets = rs.getInt("min(nbaskets)");
            s.minAssists = rs.getInt("min(nassists)");
            s.minRebounds = rs.getInt("min(nrebounds)");
            s.maxBaskets = rs.getInt("max(nbaskets)");
            s.maxAssists = rs.getInt("max(nassists)");
            s.maxRebounds = rs.getInt("max(nrebounds)");
        }
        return s;
    }


    public List<Equipo> getEquiposPorLocalidad(String localidad) throws SQLException{
        String query = "select * from team where city = ?";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setString(1, localidad);
        ResultSet rs = ps.executeQuery();
        List<Equipo> lista = new ArrayList<>();
        while (rs.next()) {
            Equipo e = new Equipo();
            e.setNombre(rs.getString("name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));
            lista.add(e);
        }
        return lista;
    }

    public List<Jugador> getJugadoresEquipo(String equipo) throws SQLException {
        String query = "select * from team t, player e where e.team=t.name and e.team = ?";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setString(1, equipo);
        ResultSet rs = ps.executeQuery();
        List<Jugador> lista = new ArrayList<>();
        while (rs.next()) {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("e.name"));
            j.setAsistencias(rs.getInt("nassists"));
            j.setCanastas(rs.getInt("nbaskets"));
            Equipo e = new Equipo();
            e.setNombre(rs.getString("t.name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));
            j.setEquipo(e);
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
            lista.add(j);
        }
        rs.close();
        return lista;
    }

    public List<Jugador> getJugadoresEquipoPos(String equipo, String posicion) throws SQLException {
        String query = "select * from team t, player e where e.team=t.name and e.team = ? and position = ?";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setString(1, equipo);
        ps.setString(2, posicion);
        ResultSet rs = ps.executeQuery();
        List<Jugador> lista = new ArrayList<>();
        while (rs.next()) {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("e.name"));
            j.setAsistencias(rs.getInt("nassists"));
            j.setCanastas(rs.getInt("nbaskets"));
            Equipo e = new Equipo();
            e.setNombre(rs.getString("t.name"));
            e.setFechaCreacion(rs.getDate("creation").toLocalDate());
            e.setLocalidad(rs.getString("city"));
            j.setEquipo(e);
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
            lista.add(j);
        }
        rs.close();
        return lista;
    }
}
