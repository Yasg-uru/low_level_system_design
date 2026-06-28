package com.lld.patterns.behavioral;

import com.lld.patterns.behavioral.cor.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Chain of Responsibility Design Pattern Tests")
class ChainOfResponsibilityTest {

    private IHandler supportChain;
    private IHandler l1;
    private IHandler l2;
    private IHandler l3;

    @BeforeEach
    void setUp() {
        l1 = new Level1Support();
        l2 = new Level2Support();
        l3 = new Level3Support();

        // Establish Chain: L1 -> L2 -> L3
        l1.setNext(l2).setNext(l3);
        supportChain = l1;
    }

    @Test
    @DisplayName("Level 1 Support should handle password reset request")
    void testLevel1SupportHandlesPasswordReset() {
        String result = supportChain.handle("password reset");
        assertNotNull(result);
        assertTrue(result.contains("[Level 1 Support]"));
        assertTrue(result.contains("password reset"));
    }

    @Test
    @DisplayName("Level 2 Support should handle bug report request via escalation")
    void testLevel2SupportHandlesBugReport() {
        String result = supportChain.handle("bug report");
        assertNotNull(result);
        assertTrue(result.contains("[Level 2 Support]"));
        assertTrue(result.contains("bug report"));
    }

    @Test
    @DisplayName("Level 3 Support should handle billing issue request via escalation")
    void testLevel3SupportHandlesBillingIssue() {
        String result = supportChain.handle("billing issue");
        assertNotNull(result);
        assertTrue(result.contains("[Level 3 Support]"));
        assertTrue(result.contains("billing issue"));
    }

    @Test
    @DisplayName("Terminal fallback should handle unsupported request")
    void testTerminalFallbackHandlesServerCrash() {
        String result = supportChain.handle("server crash");
        assertNotNull(result);
        assertTrue(result.contains("End of chain reached"));
        assertTrue(result.contains("server crash"));
    }

    @Test
    @DisplayName("Independent handler should fallback directly if not chained")
    void testIndependentHandlerFallback() {
        IHandler independentL2 = new Level2Support();
        String result = independentL2.handle("password reset");
        assertNotNull(result);
        assertTrue(result.contains("End of chain reached"));
    }
}
