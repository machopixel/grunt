package net.goodtwist.dev.grunt.cassandra;

/**
 * Created by Diego on 8/9/2015.
 */
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.BuiltStatement;

/**
 * Manages the lifecycle of the Cassandra Cluster instance, ensuring that it is appropriately
 * closed when the application terminates.
 */
public class CassandraManager {
    private Cluster cluster;
    private Session session;

    public CassandraManager() {
        this.cluster = Cluster.builder().addContactPoint("192.168.1.4").withPort(9042).build();
        this.session = cluster.connect("GoodTwist");
    }

    public Cluster getCluster(){
        return this.cluster;
    }

    public ResultSet executeQuery(BuiltStatement query) throws Exception{
        ResultSet result = null;
        try{
            result = session.execute(query);
        }catch (Exception e){

        }

        return result;
    }
}