package com.cassandra;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.*;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;

public class CassandraMain {

	public static void main(String[] args) {
//			création du cluster (~ pool de connection pour redis):
			Cluster cluster = HFactory.getOrCreateCluster("test-cluster" , "localhost:9160");

//			création du keyspace
			Keyspace ksp = HFactory.createKeyspace("dtasks", cluster);
			
//			insertion et modification de données
			
			Mutator<String> mutator = HFactory.createMutator(ksp, StringSerializer.get());
			
			HColumn<String, String> nomColumn = HFactory.createStringColumn("nom", "Julien2");
			HColumn<String, String> prenomColumn = HFactory.createStringColumn("prenom", "Alain2");
			
			mutator.addInsertion("jules2", "users", nomColumn);
			mutator.addInsertion("jules2", "users", prenomColumn);
			
			mutator.execute();
		
		CqlQuery<String, String, String> cqlQuery = new CqlQuery<String,String,String>(ksp,StringSerializer.get(), StringSerializer.get(),StringSerializer.get());
		
		cqlQuery.setQuery("select * from users");
		
		QueryResult<CqlRows<String,String,String>> queryResult = cqlQuery.execute();
		
		for(Row<String, String, String> row: queryResult.get().getList()){
			HColumn<String, String> nom = row.getColumnSlice().getColumnByName("nom");
			HColumn<String, String> prenom = row.getColumnSlice().getColumnByName("prenom");
			
			StringBuilder sb = new StringBuilder ("key = "+ row.getKey());
			
			if(nom !=null){
				sb.append(" "+nom.getValue());
			}
			
			if(prenom != null){
				sb.append(" "+prenom.getValue());
			}
			System.out.println(sb.toString());
		}
	}
}
