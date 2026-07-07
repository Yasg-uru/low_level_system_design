package com.lld.patterns.behavioral;

import com.lld.patterns.behavioral.interpreter.AndExpression;
import com.lld.patterns.behavioral.interpreter.IExpression;
import com.lld.patterns.behavioral.interpreter.NotExpression;
import com.lld.patterns.behavioral.interpreter.OrderContext;
import com.lld.patterns.behavioral.interpreter.OrExpression;
import com.lld.patterns.behavioral.interpreter.StatusEqualsExpression;
import com.lld.patterns.behavioral.interpreter.TotalGreaterThanExpression;
import com.lld.patterns.behavioral.interpreter.TypeEqualsExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Interpreter Design Pattern Tests")
class InterpreterTest {

    @Test
    @DisplayName("High value confirmed order should be eligible")
    void testHighValueConfirmedOrder() {
        IExpression highValueConfirmed = new AndExpression(
                new TotalGreaterThanExpression(1000),
                new StatusEqualsExpression("confirmed")
        );

        OrderContext order = new OrderContext("confirmed", 1500, "regular", "user1");

        assertTrue(highValueConfirmed.interpret(order));
    }

    @Test
    @DisplayName("Premium order should be eligible through OR branch")
    void testPremiumOrder() {
        IExpression highValueConfirmed = new AndExpression(
                new TotalGreaterThanExpression(1000),
                new StatusEqualsExpression("confirmed")
        );
        IExpression eligibleForDiscount = new OrExpression(
                highValueConfirmed,
                new TypeEqualsExpression("premium")
        );

        OrderContext order = new OrderContext("pending", 500, "premium", "user2");

        assertTrue(eligibleForDiscount.interpret(order));
    }

    @Test
    @DisplayName("Regular low value order should not be eligible")
    void testRegularOrder() {
        IExpression highValueConfirmed = new AndExpression(
                new TotalGreaterThanExpression(1000),
                new StatusEqualsExpression("confirmed")
        );
        IExpression eligibleForDiscount = new OrExpression(
                highValueConfirmed,
                new TypeEqualsExpression("premium")
        );

        OrderContext order = new OrderContext("pending", 200, "regular", "user3");

        assertFalse(eligibleForDiscount.interpret(order));
    }

    @Test
    @DisplayName("Not expression should invert a matching condition")
    void testNotExpression() {
        IExpression notPremium = new NotExpression(new TypeEqualsExpression("premium"));
        OrderContext order = new OrderContext("confirmed", 300, "regular", "user4");

        assertTrue(notPremium.interpret(order));
    }
}