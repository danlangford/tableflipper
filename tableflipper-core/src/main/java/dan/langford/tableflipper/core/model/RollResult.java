package dan.langford.tableflipper.core.model;

public class RollResult {

    private String expr;
    private Integer result;

    public RollResult(String expr, Integer result) {
        this.expr = expr;
        this.result = result;
    }

    public RollResult() {
    }

    public String toString(){
        return expr+'='+result;
    }

    public String getExpr() {
        return this.expr;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
