package persistence;

import model.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAM on 13/1/17.
 */
public class BasketJDBC {

    private Connection conexion;


    public List<Jugador> selectAllJugadores() throws SQLException {
        List<Jugador> jugadores = new ArrayList<>();
        String query = "select * from cocinero";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Jugador j = new Jugador();
            j.setNombre(rs.getString("name"));
            j.setAsistencias(rs.getInt("nasists"));
            j.setNacimiento(rs.getDate("birth").toLocalDate());
            j.setCanastas(rs.getInt("nbaskets"));
            j.setPosicion(rs.getString("position"));
            j.setRebotes(rs.getInt("nrebounds"));
         //   j.setEquipo(rs.getString());

            jugadores.add(j);
        }
        rs.close();
        st.close();
        return jugadores;
    }

    public void insertJugador(Jugador j) throws SQLException {
        String insert = "insert into cocinero values (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conexion.prepareStatement(insert);
        // Vamos dando valores a los interrogantes
        ps.setString(1, j.getNombre());
        ps.setDate(2, java.sql.Date.valueOf(j.getNacimiento()));
        ps.setString(3, j.getSexo());
        ps.setInt(4, j.getEdad());
        ps.setInt(5, j.getExperiencia());
        ps.setString(6, j.getEspecialidad());
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
