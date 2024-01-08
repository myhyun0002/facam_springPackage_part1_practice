package com.example.oop_practice;

import com.example.oop_practice.calculate.PositiveNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CalculatorTest {
//
//    @DisplayName("덧셈 연산을 수행한다.")
//    @Test
//    void additionTest() {
//        int result = Calculator.calculate(1,"+",2);
//        assertThat(result).isEqualTo(3);
//    }
//
//    @DisplayName("뺄셈을 수행한다.")
//    @Test
//    void subtractionTest() {
//        int result = Calculator.calculate(1,"-",2);
//        assertThat(result).isEqualTo(-1);
//    }

    @DisplayName("연산자 한번에 test")
    @ParameterizedTest()
    @MethodSource("formulaAndResult")
    void calculateTest(int operand1, String operator, int operand2, int result) {
        int calculateResult = Calculator.calculate(new PositiveNumber(operand1),operator,
                new PositiveNumber(operand2));
        assertThat(calculateResult).isEqualTo(result);
    }

    private static Stream<Arguments> formulaAndResult(){
        return Stream.of(
                arguments(1,"+",3,4),
                arguments(1,"-",3,-2),
                arguments(1,"*",2,2),
                arguments(2,"/",1,2)
        );
    }

    @DisplayName("나눗셈에서 0을 나누는 경우 IllegalArgument 예외를 발생시킨다.")
    @Test
    void calculateExceptionTest() {
        assertThatCode(() -> Calculator.calculate(new PositiveNumber(2),"/",
                new PositiveNumber(0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0 또는 음수를 전달할 수 없습니다.");
    }
}
