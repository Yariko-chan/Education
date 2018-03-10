import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 26.01.2017.
 */
public class UFTest {

    @Test
    public void connected() throws Exception {
        int N = 10;
        UF uf = new OptimalQU(N);
        int [][] pq = {{4, 3, 6, 9, 2, 8, 5, 7, 6, 1, 6},
                {3, 8, 5, 4, 1, 9, 0, 2, 1, 0, 7}};
        for (int i = 0; i < pq[0].length; i++){
            int p = pq[0][i];
            int q = pq[1][i];
            if (!uf.connected(p, q)){
                uf.union(p, q);
                System.out.println(p + " " + q);
            }
        }
        assertTrue(uf.connected(4, 3));
        assertTrue(uf.connected(4, 8));
        assertTrue(uf.connected(4, 9));
        assertTrue(uf.connected(6, 0));
        assertTrue(uf.connected(6, 1));
        assertTrue(uf.connected(6, 5));
        assertTrue(uf.connected(6, 7));

        assertFalse(uf.connected(4, 1));
        assertFalse(uf.connected(8, 0));
        assertFalse(uf.connected(9, 5));
        assertFalse(uf.connected(3, 2));
    }

}