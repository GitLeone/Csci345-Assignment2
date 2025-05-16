import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;


public class Deck{
    private Map<String, SceneCard> sceneDeck = new HashMap<>();

    public Deck(Map<String, SceneCard> sceneDeck){
        this.sceneDeck = new HashMap<>(sceneDeck);
    }

    public void removeCardFromDeck(SceneCard card){
        this.sceneDeck.remove(card.getName());
    }

    public SceneCard drawRandomSceneCard(){
        List<SceneCard> cardList = new ArrayList<>(sceneDeck.values());
        Random random = new Random();
        int deckIndex = random.nextInt(cardList.size());
        SceneCard card = cardList.get(deckIndex);
        removeCardFromDeck(card);
        return card;
    }
}