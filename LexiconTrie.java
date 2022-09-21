//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Iterator;
import java.util.Scanner;

//STUDENTS: fill in the following class with your implementation.
//We have included the names of the methods that you should fill in
//as a starting point.

public class LexiconTrie implements Lexicon {

  /* single letter stored in this node */
  protected char letter;

  /* root node of trie */
  protected LexiconNode root;

  /* count of words stored in lexicon */
  protected int wordsCount;

  /**
  * Method to construct a new lexicon trie
  */
  LexiconTrie() {
    root = new LexiconNode(' ', false);
  }

  /**
  * Method to add the specified word to this lexicon
  * It returns true if the word was added (i.e. previously did
  * not appear in this lexicon) and false otherwise.
  * @pre string is not empty and node is not null
  * @param word of type String
  * @return boolean indicating whether word was added (true) or not (false)
  * @post return a boolean
  */
  public boolean addWord(String word) {
    // convert word to lowercase
    word = word.toLowerCase();

    // if the lexicon already contains the word return false
    if (containsWord(word)) {
      return false;
    }

    // set current node equal to root node
    LexiconNode current = root;
    // traverse through word
    for (int i = 0; i < word.length(); i++) {
      // set child node equal to the current node's child w/corresponding char in word
      LexiconNode child = current.getChild(word.charAt(i));
      // if the child node is null, assign a new node with that char to children
      // add child node as child of current
      if (child == null) {
        child = new LexiconNode(word.charAt(i), false);
        current.addChild(child);
      }
      // update current to the child to move down the trie
      current = child;
    }
    // if current node's isWord flag is true then increment word count and return true
    current.setIsWord(true);
    wordsCount++;
    return true;
  }

  /**
  * Method to open the specified file, expecting a
  * text file containing one word per line, and adds each word
  * to this lexicon.
  * @pre String is not empty
  * @param String storing the filename
  * @return int count representing number of words added to lexicon
  * @post returns an int
  */
  public int addWordsFromFile(String filename) {
    // create scanner object
    Scanner sc = new Scanner(new FileStream(filename));
    // initialize count var as 0
    int count = 0;


    while (sc.hasNextLine()) {
      // store each line in file in word var
      String word = sc.nextLine();
      // convert word to lowercase
      word = word.toLowerCase();
      // check to see if word is a blank space then exit out of loop bc no more words in file
      if (word.equals("")) {
        break;
      }
      // add each word to lexicon
      addWord(word);
      //increment count
      count++;
    }
    return count;
  }

  /**
  * Method to remove specified word from lexicon. If the word appears in the lexicon, it is removed,
  * and true is returned. If the word was not contained in the lexicon,
  * the lexicon is unchanged and false is returned.
  * @pre String is not empty
  * @param String representing word that needs to be removed from lexicon
  * @return boolean, if the word appears in the lexicon and it is removed,
  * true is returned. If the word was not contained in the lexicon, then false is returned
  * @post returns a boolean
  */
  public boolean removeWord(String word) {
    // check to see if the word is in the lexicon
    if (containsWord(word)) {
      // call find to assign the word to a node
      LexiconNode lastNode = find(word);
      // set last node of word to false
      lastNode.setIsWord(false);
      // decrement wordsCount
      wordsCount--;
      return true;
    }
    return false;
  }


  /**
  * Method to find the last node of a word stored in lexicon.
  * @pre String is not empty
  * @param String representing word that's node needs to be found in lexicon
  * @return last LexiconNode of word
  * @post returns LexiconNode
  */
  public LexiconNode find(String word) {
    word = word.toLowerCase();
    // set current node equal to root node
    LexiconNode current = root;
    // traverse through word
    for (int i = 0; i < word.length(); i++) {
      // set child node equal to the current node's child w/corresponding char in word
      LexiconNode child = current.getChild(word.charAt(i));
      // if child is null then return null (word does not exist in lexicon)
      if (child == null) {
        return null;
      }
      // update current to the child to move down the trie
      current = child;
    }
    return current;
  }

  /**
  * Method to access the number of words stored in the lexicon
  * @return int, number of words stored in lexicon
  * @post returns int
  */
  public int numWords() {
    return wordsCount;
  }

  /**
  * Method to check if the lexicon includes specified word
  * @pre String word is not empty
  * @param String representing word
  * @return boolean, returns true if the specified word exists in this lexicon, false otherwise
  * @post returns boolean
  */
  public boolean containsWord(String word) {
    // store node of word in wordie
    LexiconNode wordie = find(word);
    // if wordie is not null and its isWord flag is up, then return true, false otherwise
    return wordie != null && wordie.isWord();
  }

  /**
  * Method to check if the lexicon includes specified prefix
  * @pre String is not empty
  * @param String representing prefix
  * @return boolean, returns true if the specified prefix exists in this lexicon, false otherwise
  * @post returns boolean
  */
  public boolean containsPrefix(String prefix) {
    // store node of word in prefixie
    LexiconNode prefixie = find(prefix);
    // if prefixie is not null return true, false otherwise
    return prefixie != null;
  }

  /**
  * Helper method for iterator to traverse through trie
  * @pre LexiconNode root is not null
  * @param Vector of strings representing words stored in lexicon
  * @param LexiconNode root representing root of trie
  * @param String representing the string so far for building a word
  */
  public void iteratorHelper(Vector<String> words, LexiconNode root, String strSoFar) {
    // append the current root's letter to strSoFar
     strSoFar += root.getLetter();
     // if the root's isWord flag is up, add the strSoFar to words vector
     if (root.isWord()) {
       words.add(strSoFar);
     }

     // traverse through root's children vector
     for (int i = 0; i < root.getChildren().size(); i++) {
       // call iteratorHelper recursively and pass through each one of root's child nodes
       iteratorHelper(words, root.getChildren().get(i), strSoFar);
     }
  }

  /**
  * Method for iterator to traverse through trie
  * @pre node is not null
  * @return iterator over all words stored in lexicon
  * @post returns iterator of type string
  */
  public Iterator<String> iterator() {
    // initialize empty string for strSoFar
    String strSoFar = "";
    // initialize empty vector for words vec
    Vector<String> words = new Vector<String>();
    //initialize root var for node root
    LexiconNode root = this.root;

    // call helper
    iteratorHelper(words, root, strSoFar);

    return words.iterator();
  }
}
