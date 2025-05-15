public class Deck{
    private Map<String, SceneCard> sceneDeck = new HashMap<>();
    private Map<String, SceneCard> currentDeck = new HashMap<>(); //acts as the deck that is drawn from to preserve a copy of the original

    public Deck(Map<String, SceneCard> sceneDeck){
        this.sceneDeck = new HashMap<>(sceneDeck);
        this.currentDeck = new HashMap<>(sceneDeck);
    }
    
    public void addCardToSet(SceneCard card, Set set){
        set.setSceneCard(card);
        removeCardFromDeck(card);
    }

    public void removeCardFromDeck(SceneCard card){
        currentDeck.remove(card.getName());
    }

    public void resetDeck(){
        currentDeck = new HashMap<>(sceneDeck);

    }

    public SceneCard drawRandomSceneCard(){
        //returns a random card from the deck
        //will be used when dealing cards to sets
    }
}