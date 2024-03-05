class InnerTyping implements Visitor {

    // Stack to keep track of variable scopes
    private Stack<Map<String, Variable>> scopeStack = new Stack<>();

    // Constructor
    public InnerTyping(TFile tfile) {
        this.tfile = tfile;
        // Initialize with global scope
        scopeStack.push(new HashMap<>());
    }

    // Method to enter a new scope
    private void enterScope() {
        scopeStack.push(new HashMap<>());
    }

    // Method to exit the current scope
    private void exitScope() {
        scopeStack.pop();
    }

    // Method to add a variable to the current scope
    private void addVariable(String name, Variable variable) {
        scopeStack.peek().put(name, variable);
    }

    // Method to retrieve a variable from the current scope or outer scopes
    private Variable getVariable(String name) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            Map<String, Variable> scope = scopeStack.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null; // Variable not found
    }

    // Other methods...

    @Override
    public void visit(Sblock s) {
        enterScope();
        // Visit statements in the block
        for (Stmt stmt : s.l) {
            stmt.accept(this);
        }
        exitScope();
    }

    @Override
    public void visit(Sassign s) {
        // Check if variable is already defined in current scope
        String varName = s.x.id;
        if (getVariable(varName) != null) {
            Typing.error(s.x.loc, "Variable " + varName + " is already defined in this scope");
        }
        // Visit the assigned expression
        s.e.accept(this);
        // Add the variable to the current scope
        addVariable(varName, new Variable(/* type */));
    }

    // Other visit methods...
}
