package net.goodtwist.dev.grunt.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.BuiltStatement;

public class CassandraManager {
    private Cluster cluster;
    private Session session;

    public CassandraManager() {
        this.cluster = Cluster.builder().addContactPoint("localhost").withPort(9042).build();
        this.session = cluster.connect("GoodTwist");
    }

    public Cluster getCluster() {
        return this.cluster;
    }

    public ResultSet executeQuery(BuiltStatement query) throws Exception {
        ResultSet result = null;
        try {
            result = session.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String tokeFix(String... values) {
        StringBuilder sb = new StringBuilder();
        sb.append("token(");
        for (int i = 0; i < values.length; i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(values[i]);
        }
        sb.append(")");
        return sb.toString();
    }
}