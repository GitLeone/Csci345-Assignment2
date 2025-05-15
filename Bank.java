import java.util.HashMap;
import java.util.Map;

public class Bank{
    private Map<Integer, Integer> rankDollarCosts = new HashMap<>();
    private Map<Integer, Integer> rankCreditCosts = new HashMap<>();

    public Bank(Map<Integer, Integer> rankDollarCosts, Map<Integer, Integer> rankCreditCosts){
        this.rankDollarCosts = new HashMap<>(rankDollarCosts);
        this.rankCreditCosts = new HashMap<>(rankCreditCosts);
    }

    public int getRankDollarCost(int rank){
        return rankDollarCosts.get(rank);
    }

    public int getRankCreditCost(int rank){
        return rankCreditCosts.get(rank);
    }

    public boolean validateUpgrade(Player player, int rank, String currencyType){
        boolean result = true;
        //if rank cost is higher than currency, return false
        if(player.getRank() < rank){
            if(currencyType.equals("Dollars")){
                if((rankDollarCosts.get(rank) > player.getDollars()) || (rankDollarCosts.get(rank) == null)){
                    result = false;
                }
            }
            else if(currencyType.equals("Credits")){
                if((rankCreditCosts.get(rank) > player.getCredits()) || (rankCreditCosts.get(rank) == null)){
                    result = false;
                }            
            }
            else {
                result = false;
            }
        }
        else{
            result = false;
        }
        return result;
    }
}