package testSberB.testSberB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ru.yandex.qatools.allure.annotations.*;

@Title("Test Operation")
@Description("In this suite we will test only math operations(+-*/)")
@RunWith(Parameterized.class)
public class TestOperation {
	private static final double DELTA = 1e-15;

	// result: 1-> +; 2-> -; 3-> *; 4-> /;
	public static Object[] toDoubleArray(String[] parts) {
		Object[] string = new Object[4];
		string[0] = Double.parseDouble(parts[0]);
		string[1] = Double.parseDouble(parts[1]);
		string[3] = Double.parseDouble(parts[3]);
		switch (parts[2]) {
		case "+":
			string[2] = 1;
			break;
		case "-":
			string[2] = 2;
			break;
		case "*":
			string[2] = 3;
			break;
		case "/":
			string[2] = 4;
			break;
		default:
			break;
		}
		return string;

	}

	@Step("0) считываем данные из файла в динамический массив, "
			+ "каждый элемент которого состоит из четырех числовых значений:"
			+ "1-операнд № 1"
			+ "2-операнд № 2"
			+ "3-операция(каждой математической операции соответствует свое значение 1-> +; 2-> -; 3-> *; 4-> /"
			+ "4-ожидаемый результат")
	@Parameterized.Parameters
	public static List readFile() {

		String file = "D:\\workspace2\\testSber\\testSber.txt";
		List<Object[]> list = new ArrayList<Object[]>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = br.readLine()) != null) {
				list.add(toDoubleArray(s.split(";")));
			}
		} catch (IOException ex) {

			System.out.println(ex.getMessage());
		}

		return list;
	}

	private double x, y, operation, result;
	
	
	public TestOperation(double x, double y, double operation, double result) {

		this.x = x;
		this.y = y;
		this.operation = operation;
		this.result = result;

	}
	@Step("Проверка операции сложения")
	public void testAdd() {
		MathOperation m = new MathOperation();
		assertEquals(result, m.add(x, y), DELTA);
	}
	@Step("Проверка операции вычитания")
	public void testSubtract() {
		MathOperation m = new MathOperation();
		assertEquals(result, m.subtract(x, y), DELTA);
	}
	@Step("Проверка операции умножения")
	public void testMultiply() {
		MathOperation m = new MathOperation();
		assertEquals(result, m.multiply(x, y), DELTA);
	}

	@Step("Проверка операции деления")
	public void testDivide() {
		MathOperation m = new MathOperation();
		assertEquals(result, m.divide(x, y), DELTA);
	}
	@Step("1) проверка заданной математической операции")
	@Title("Test All Operation")
	@Description("In this test we will check +|-|*|/")
	@Test
	public void testAllOperation() {
		int i = (int) operation;
		switch (i) {
		case 1:
			testAdd();
			break;
		case 2:
			testSubtract();
			break;
		case 3:
			testMultiply();
			break;
		case 4:
			testDivide();
			break;
		}
	}

}
