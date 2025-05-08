import java.util.HashMap;
import java.util.Map;

public class Bank{
    private Map<Integer, Integer> rankDollarCosts;
    private Map<Integer, Integer> rankCreditCosts;
    public Bank(Map<Integer, Integer> rankDollarCosts, Map<Integer, Integer> rankCreditCosts){
        this.rankDollarCosts = rankDollarCosts;
        this.rankCreditCosts = rankCreditCosts;
    }

    public Bank() {
        initializeUpgradeCosts();
    }

    private void initializeUpgradeCosts() {
        //Dollar costs for upgrading ranks
        rankDollarCosts = new HashMap<>();
        rankDollarCosts.put(2, 4);
        rankDollarCosts.put(3, 10);
        rankDollarCosts.put(4, 18);
        rankDollarCosts.put(5, 28);
        rankDollarCosts.put(6, 40);
        
        //Credit costs for upgrading ranks
        rankCreditCosts = new HashMap<>();
        rankCreditCosts.put(2, 5);
        rankCreditCosts.put(3, 10);
        rankCreditCosts.put(4, 15);
        rankCreditCosts.put(5, 20);
        rankCreditCosts.put(6, 25);
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