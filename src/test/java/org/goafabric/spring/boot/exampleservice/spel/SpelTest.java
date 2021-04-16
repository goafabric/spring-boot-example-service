package org.goafabric.spring.boot.exampleservice.spel;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpelTest {

    private static final ExpressionParser expressionParser = new SpelExpressionParser();
    @Test
    public void test() {
        //ExpressionParser expressionParser = new SpelExpressionParser();
        //Expression expression = expressionParser.parseExpression("'Any string'");
        //Expression expression = expressionParser.parseExpression("#{@tenantIdBean.getId()}");

        final Expression expression = expressionParser.parseExpression("T(org.goafabric.spring.boot.exampleservice.crossfunctional.TenantRequestContext).getTenantId()");
        System.out.println("result is " + (String) expression.getValue());
        System.out.println("result is " + (String) expression.getValue());
        System.out.println("result is " + (String) expression.getValue());
    }
}
