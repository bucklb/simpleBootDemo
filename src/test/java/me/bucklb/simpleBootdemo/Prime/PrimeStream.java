package me.bucklb.simpleBootdemo.Prime;

/*
    Spin out of codewars to generate a lot of primes quickly
 */

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class PrimeStream {


    // Return if number is prime
    public static boolean isPrime(long n) {


        // bit of a double negative.  prime if ALL in 2-root(n) are NOT factors
        // - presumably will give a FALSE as soon as a proper factor is found
        return LongStream
                .rangeClosed(2, (long) Math.sqrt(n))    // don't need to go beyond the root
                .allMatch(i -> n % i != 0);             // prime if NOTHING between 2-root(n) gives no remainder on divide
    }

    public static List<Long> primeSequence(long min, long max) {

        // In terms of using isPrime
        // the more explicit way is perhaps i->isPrime(i) which could also be i->PrimeStream.isPrime(i)
        // but that can be condensed (for single argument function like isPrime) to PrimeStream::isPrime (cf System.out::println)

        return LongStream.range(min, max)
//                .filter(i->isPrime(i))
//                .filter(PrimeStream::isPrime)
                .filter(p->PrimeStream.isPrime(p))
                .boxed()
                .collect(Collectors.toList());
    }

    // check that can set (and return) value simultaneously
    public int checkReturn(){
        int a=0;
        return a=5;
    }





    /*
        A different approach to primes (build up a sequential list of primes to date
     */
    private static final int[] primes = new int[1_000_000 + 10];
    private static int primeCnt=1;
    private static int idx=0;
    static{
        // Start the ball rolling
        primes[0]=2;
        primes[1]=3;
    }

    // Spin through the primes to date and see if this is divisible by them
    private boolean prime( int candidate) {
        for( int p: primes) {
            // if p>=sqrt(c) then overshot, so not going to find a factor, so stop looking
            if(candidate < p*p) break;
            // if a prime leaves no remainder then NOT a prime
            if(candidate%p == 0) return false;
        }
        return true;
    }

    // Find next prime (after the last one we have in the array)
    private int nextPrime() {

        // Start from last known prime and add 2 (so aways odd)
        int c;
        for( c = primes[primeCnt]+2; !prime(c);c+=2){
            // nothing more to do in the loop
        }
        return primes[++primeCnt]=c;
    }

    // Get the ith prime.  Might need to further populate our list to get it though (and may want to block)
    private int getPrime(int primeIdx) {
//        System.out.println("getPrime called for " + primeIdx + "  and count=" + primeCnt);
        if( primeIdx > primeCnt) {
            // We haven't found the prime yet, so fill in the blanks
            for( int i=primeCnt+1; i<=primeIdx; i++) {
  //              System.out.println("call nextPrime with i="+i);
                nextPrime();
            }
        }
        return primes[primeIdx];
    }

    // Want an "infinite" stream of primes.
    private IntStream primeStream() {
        idx = 0;
        return IntStream.generate(() -> {
            int p=getPrime(idx++);
            return p;
        });
    }



    @Test
    public void primeStreamTest(){
        IntStream pStream = primeStream();

        pStream.forEach(System.out::println);

    }


    @Test
    public void testNextPrime() {
        int j=3;
        for( int i=1; i<=1; i++){
            System.out.println(i + " " + nextPrime());
        }
        j=11; System.out.println(j + " " + getPrime(j));
        j=9; System.out.println(j + " " + getPrime(j));
        j=13; System.out.println(j + " " + getPrime(j));
        j=14; System.out.println(j + " " + getPrime(j));
//        j=114; System.out.println(j + " " + getPrime(j));
        j=0; System.out.println(j + " " + getPrime(j));


    }



    // Not a lot to test ...
    @Test
    public void testIt(){
        long n=71;
        System.out.println(">> " + n + " " + isPrime(n));
        List<Long> p = primeSequence(2,13);
        System.out.println(p.size());

        // do output using     System.out::println    _or_    x->System.out.println(x)
        p.stream().forEach(x->System.out.println(x));

        System.out.println(" -> "+checkReturn());



    }

}
