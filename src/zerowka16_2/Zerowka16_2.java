package zerowka16_2;

import java.io.*;
/*
Napisać funkcję Sinus(String) przetwarzającą plik z wartościami typu double
(nazwa pliku jest przekazana jako parametr) wyliczając wartości 
funkcji sinus wg wzoru:
*/
public class Zerowka16_2 {

   static double sin( double EPS, double x) {
       double sinus = 0,
              skł   = x;
       int    n = 0; 
       
       while( Math.abs(skł) >= EPS){
          sinus += skł;
          n++;
          skł = -(skł*x*x)/(2*n*(2*n+1));
       } 
       return sinus;
   }
    
   static void Sinus( String nPlik ) throws IOException
   {
       long poz;
       double sinus;
       RandomAccessFile plik = null;
       try{
           plik = new RandomAccessFile( nPlik, "rw" );
           while( true ){
             poz   = plik.getFilePointer();   
             plik.readDouble();                     
             
             sinus = sin( plik.readDouble(), plik.readDouble());
             
             plik.seek( poz );
             plik.writeDouble( sinus );
             plik.readDouble();
             plik.readDouble();
           }
       }
       catch( EOFException e) { if( plik != null ) plik.close();}
       catch( FileNotFoundException e) {}
       catch( IOException e)  { if( plik != null ) plik.close();}

   }
    public static void main(String[] args) throws IOException 
    {
       String nPlik = "plik.bin"; 
       RandomAccessFile plik = null;
       try{
           plik = new RandomAccessFile( nPlik, "rw");
           plik.writeDouble( 0.0 );
           plik.writeDouble( 0.01 );
           plik.writeDouble( Math.PI );
           plik.writeDouble( 0 );
           plik.writeDouble( 0.000001 );
           plik.writeDouble( Math.PI/2 );
           plik.writeDouble( 0 );
           plik.writeDouble( 0.000000001 );
           plik.writeDouble( Math.PI/6 );
           plik.close();
       }
       catch( IOException e)  { if( plik != null ) plik.close();}
       
       Sinus( nPlik );
       
       double x, EPS, sinus;
    
       try{  
           plik = new RandomAccessFile( nPlik, "r");
           while(true){
             
               sinus = plik.readDouble();
               EPS = plik.readDouble();
               x = plik.readDouble();
               System.out.println( x + "; " + EPS + "; " + sinus );
           }
       }    
       catch( EOFException e)  { if( plik != null ) plik.close();}
       
    }
}
