//package L3.Tests;
//
//import L3.Polynomial;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class PolynomialTest {
//
//    @Test
//    void testAdd() {
//        Polynomial p1 = new Polynomial();
//        p1.terms.add(p1.new PolynomialTerm(2, 2));
//        p1.terms.add(p1.new PolynomialTerm(3, 1));
//        p1.terms.add(p1.new PolynomialTerm(1, 0));
//
//        Polynomial p2 = new Polynomial();
//        p2.terms.add(p2.new PolynomialTerm(1, 2));
//        p2.terms.add(p2.new PolynomialTerm(2, 1));
//        p2.terms.add(p2.new PolynomialTerm(4, 0));
//
//        Polynomial expected = new Polynomial();
//        expected.terms.add(expected.new PolynomialTerm(3, 2));
//        expected.terms.add(expected.new PolynomialTerm(5, 1));
//        expected.terms.add(expected.new PolynomialTerm(5, 0));
//
//        Polynomial result = p1.add(p2);
//        assertEquals(expected.terms.size(), result.terms.size());
//
//        for (int i = 0; i < expected.terms.size(); i++) {
//            assertEquals(expected.terms.get(i).coefficient, result.terms.get(i).coefficient);
//            assertEquals(expected.terms.get(i).degree, result.terms.get(i).degree);
//        }
//    }
//
//    @Test
//    void testMultiply() {
//        Polynomial p1 = new Polynomial();
//        p1.terms.add(p1.new PolynomialTerm(2, 2));
//        p1.terms.add(p1.new PolynomialTerm(3, 1));
//        p1.terms.add(p1.new PolynomialTerm(1, 0));
//
//        Polynomial p2 = new Polynomial();
//        p2.terms.add(p2.new PolynomialTerm(1, 2));
//        p2.terms.add(p2.new PolynomialTerm(2, 1));
//        p2.terms.add(p2.new PolynomialTerm(4, 0));
//
//        Polynomial expected = new Polynomial();
//        expected.terms.add(expected.new PolynomialTerm(2, 4));
//        expected.terms.add(expected.new PolynomialTerm(7, 3));
//        expected.terms.add(expected.new PolynomialTerm(16, 2));
//        expected.terms.add(expected.new PolynomialTerm(17, 1));
//        expected.terms.add(expected.new PolynomialTerm(13, 0));
//
//        Polynomial result = p1.multiply(p2);
//        assertEquals(expected.terms.size(), result.terms.size());
//
//        for (int i = 0; i < expected.terms.size(); i++) {
//            assertEquals(expected.terms.get(i).coefficient, result.terms.get(i).coefficient);
//            assertEquals(expected.terms.get(i).degree, result.terms.get(i).degree);
//        }
//    }
//
//    @Test
//    void testMultiplyByScalar() {
//        Polynomial p = new Polynomial();
//        p.terms.add(p.new PolynomialTerm(2, 2));
//        p.terms.add(p.new PolynomialTerm(3, 1));
//        p.terms.add(p.new PolynomialTerm(1, 0));
//
//        double scalar = 2;
//
//        Polynomial expected = new Polynomial();
//        expected.terms.add(expected.new PolynomialTerm(4, 2));
//        expected.terms.add(expected.new PolynomialTerm(6, 1));
//        expected.terms.add(expected.new PolynomialTerm(2, 0));
//
//        Polynomial result = p.multiplyByScalar(scalar);
//        assertEquals(expected.terms.size(), result.terms.size());
//
//        for (int i = 0; i < expected.terms.size(); i++) {
//            assertEquals(expected.terms.get(i).coefficient, result.terms.get(i).coefficient);
//            assertEquals(expected.terms.get(i).degree, result.terms.get(i).degree);
//        }
//    }
//
//    @Test
//    void testEvaluate() {
//        Polynomial p = new Polynomial();
//        p.terms.add(p.new PolynomialTerm(2, 2));
//        p.terms.add(p.new PolynomialTerm(3, 1));
//        p.terms.add(p.new PolynomialTerm(1, 0));
//
//        double x = 2;
//
//        double expected = 17;
//        double result = p.evaluate(x);
//        assertEquals(expected, result);
//    }
//
//    @Test
//    void testPrint() {
//        Polynomial p = new Polynomial();
//        p.terms.add(p.new PolynomialTerm(2, 2));
//        p.terms.add(p.new PolynomialTerm(-3, 1));
//        p.terms.add(p.new PolynomialTerm(1, 0));
//
//        String expected = "2.0x^2 -3.0x^1 +1.0x^0 ";
//        String result = TestUtils.captureStandardOutput(() -> p.print());
//        assertEquals(expected, result.trim());
//    }
//}
//
