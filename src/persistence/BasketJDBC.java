package persistence;

import model.Equipo;
import model.Jugador;

import java.sql.*;
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

}
