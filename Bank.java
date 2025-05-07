import java.util.Map;

public class Bank{
    private Map<Integer, Integer> rankDollarCosts;
    private Map<Integer, Integer> rankCreditCosts;
    public Bank(Map<Integer, Integer> rankDollarCosts, Map<Integer, Integer> rankCreditCosts){
        this.rankDollarCosts = rankDollarCosts;
        this.rankCreditCosts = rankCreditCosts;
    }
    public int getRankDollarCost(int rank){
        return rankDollarCosts.get(rank);
    }
    public int getRankCreditCost(int rank){
        return rankCreditCosts.get(rank);
    }
    public boolean validateUpgrade(int rank, int currency, String currencyType){
        boolean result = true;
        //if rank cost is higher than currency, return false
        if(currencyType.equals("Dollars")){
            if((rankDollarCosts.get(rank) > currency) || (rankDollarCosts.get(rank) == null)){
                result = false;
            }
        }
        else if(currencyType.equals("Credits")){
            if((rankCreditCosts.get(rank) > currency) || (rankCreditCosts.get(rank) == null)){
                result = false;
            }            
        }
        else {
            result = false;
        }
        return result;
    }
}