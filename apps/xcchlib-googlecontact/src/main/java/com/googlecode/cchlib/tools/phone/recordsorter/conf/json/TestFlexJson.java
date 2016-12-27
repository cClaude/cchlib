package com.googlecode.cchlib.tools.phone.recordsorter.conf.json;

import java.util.Arrays;
import java.util.List;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class TestFlexJson
{
    private TestFlexJson()
    {
        // App
    }

    @SuppressWarnings({
        "squid:S106","squid:S106" // CLI App
        })
    public static void main( final String[] args )
    {
        final ProductInfo p1 = new ProductInfo( 1, "Stack" );
        final ProductInfo p2 = new ProductInfo( 2, "Overflow" );
        final List<ProductInfo> infos = Arrays.asList( p1, p2 );

        final String s = new JSONSerializer().exclude( "*.class", "description" ).serialize( infos );

        System.out.println( s );
        // => [{"name":"Stack","productId":1},{"name":"Overflow","productId":2}]

        final List<ProductInfo> ls = new JSONDeserializer<List<ProductInfo>>().deserialize( s );
        System.out.println( ls );
        // => [{name=Stack, productId=1}, {name=Overflow, productId=2}]
    }

    public static class ProductInfo
    {
        private final int    id;
        private final String name;
        private String desc; // Not used, to demonstrate "exclude".

        public ProductInfo( final int id, final String name )
        {
            this.id   = id;
            this.name = name;
        }

        public int getProductId()
        {
            return this.id;
        }

        public String getName()
        {
            return this.name;
        }

        public String getDescription()
        {
            return this.desc;
        }
    }
}
