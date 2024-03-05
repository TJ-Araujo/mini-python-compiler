class InnerTyping implements Visitor {

    // Stack to keep track of variable scopes
    private Stack<Map<String, Variable>> scopeStack = new Stack<>();

    // Constructor
    public InnerTyping(TFile tfile) {
        this.tfile = tfile;
        // Initialize with global scope
        enterScope();
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

    // Method to check if a variable is global
    private boolean isGlobalVariable(String name) {
        // Global variables are defined in the outermost scope
        return scopeStack.size() == 1 && scopeStack.peek().containsKey(name);
    }

    // Override visit methods to handle scoping rules appropriately
    // ...
}
