/**
 * CHAIN OF RESPONSIBILITY DESIGN PATTERN
 * * Intent: Avoid coupling the sender of a request to its receiver by giving more 
 * than one object a chance to handle the request. Chain the receiving objects 
 * and pass the request along the chain until an object handles it.
                                * l1.handle()

                                ↓

                                Level1

                                ↓

                                super.handle()

                                ↓

                                BaseHandler.handle()

                                ↓

                                nextHandler.handle()

                                ↓

                                Level2.handle()

                                ↓

                                Return
 */

// --- 1. THE HANDLER INTERFACE ---
interface IHandler {
    setNext(handler: IHandler): IHandler; // Links the next handler in the execution chain
    handle(request: string): string;      // Evaluates and processes the request
}

// --- 2. THE BASE HANDLER (Boilerplate Chaining Logic) ---
abstract class BaseHandler implements IHandler {
    private nextHandler: IHandler | null = null;

    // Returns the passed handler to allow fluent interface builder chaining: h1.setNext(h2).setNext(h3)
    public setNext(handler: IHandler): IHandler {
        this.nextHandler = handler;
        return handler;
    }

    public handle(request: string): string {
        // If a downstream link exists, pass the request forward
        if (this.nextHandler) {
            return this.nextHandler.handle(request);
        }

        // Terminal fallback state if the request reaches the end of the chain unhandled
        return `End of chain reached. Nobody could handle: "${request}"`;
    }
}

// --- 3. CONCRETE HANDLERS ---

class Level1Support extends BaseHandler {
    public override handle(request: string): string {
        if (request === "password reset") {
            return `[Level 1 Support]: Successfully processed "password reset" request.`;
        }
        // Cannot handle; delegate to parent class forwarder
        return super.handle(request);
    }
}

class Level2Support extends BaseHandler {
    public override handle(request: string): string {
        if (request === "bug report") {
            return `[Level 2 Support]: Logged and processed "bug report" lifecycle request.`;
        }
        return super.handle(request);
    }
}

class Level3Support extends BaseHandler {
    public override handle(request: string): string {
        if (request === "billing issue") {
            return `[Level 3 Support]: Escalation resolved: "billing issue" processed.`;
        }
        return super.handle(request);
    }
}

// --- 4. DEMONSTRATION RUN ---

function runChainOfResponsibilityDemo(): void {
    // Instantiate handlers
    const l1 = new Level1Support();
    const l2 = new Level2Support();
    const l3 = new Level3Support();

    // Establish Chain Relationships: L1 -> L2 -> L3
    l1.setNext(l2).setNext(l3);

    console.log("--- Executing Chain Pipeline ---");

    // Handled instantly by L1
    console.log(l1.handle("password reset"));

    // Passed past L1 -> Evaluated and captured by L2
    console.log(l1.handle("bug report"));

    // Passed past L1 and L2 -> Evaluated and captured by L3
    console.log(l1.handle("billing issue"));

    // Evaluated by all layers -> Falls out through base handler terminal fallback block
    console.log(l1.handle("server crash"));
}

runChainOfResponsibilityDemo();

export {};