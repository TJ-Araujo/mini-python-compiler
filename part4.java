public void checkListRangeExpression(Expr e) {
    // Check if the expression is a function call to 'list'
    if (e instanceof Ecall) {
        Ecall ecall = (Ecall) e;
        if (ecall.fun instanceof Eident && ((Eident) ecall.fun).id.equals("list")) {
            // Check if the argument to 'list' is a function call to 'range'
            if (ecall.args.size() == 1 && ecall.args.get(0) instanceof Ecall) {
                Ecall innerCall = (Ecall) ecall.args.get(0);
                if (innerCall.fun instanceof Eident && ((Eident) innerCall.fun).id.equals("range")) {
                    // It matches the expected pattern
                    return;
                }
            }
        }
    }
    // If it doesn't match the expected pattern, throw an exception
    throw new IllegalArgumentException("Invalid usage of built-in functions list and range.");
}
