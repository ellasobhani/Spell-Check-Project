//I am the sole author of the work in this repository.
import structure5.*;
import java.util.Iterator;

/*
* An implementation of LexiconNode that supports methods to alter the
* LexiconNode
*/
class LexiconNode implements Comparable<LexiconNode> {

  /* single letter stored in this node */
  protected char letter;

  /* true if this node ends some path that defines a valid word */
  protected boolean isWord;

  /* a data structure for keeping track of the children of
  this LexiconNode */
  protected Vector<LexiconNode> children;

  /**
  * LexiconNode constructor
  * @post initializes letter stored in the node, boolean
  * isWord, and vector of LexiconNode children
  */
  LexiconNode(char theLetter, boolean theIsWord) {
    letter = theLetter;
    isWord = theIsWord;
    children = new Vector<LexiconNode>();
  }

  /**
  * Method to access letter stored in a lexicon node
  * @pre node is not null
  * @post returns letter stored in node
  * @return letter of type char stored in node
  */
  public char getLetter() {
    return letter;
  }

  /**
  * Method to access vector of children stored in a lexicon node
  * @pre node is not null
  * @post vector of lexicon node children stored in node
  * @return vector of type lexicon node
  */
  public Vector<LexiconNode> getChildren() {
    return children;
  }

  /**
  * Method to compare this LexiconNode to another
  * @pre both nodes are not null
  * @post int is returned
  * @param o of type LexiconNode to compare to this of type LexiconNode
  * @return int is returned representing difference between letters of each node
  */
  public int compareTo(LexiconNode o) {
    return this.getLetter() - o.getLetter();
  }

  /**
  * Method to access boolean of lexicon node whether it makes a word or not
  * @pre node is not null
  * @post boolean is returned
  * @return boolean is returned, true if node is word and false if not word
  */
  public boolean isWord() {
    return this.isWord;
  }

  /**
  * Method to set the isWord flag of a node
  * @pre both nodes are not null
  * @param bool of type boolean to set to the isWord boolean of the node
  */
  public void setIsWord(boolean bool) {
    this.isWord = bool;
  }

  /**
  * Method to add a child node to the vector of child nodes children for a node
  * @pre both nodes are not null
  * @param ln of type LexiconNode to add to children vector
  */
  public void addChild(LexiconNode ln) {
    int i;
    for (i = 0; i < children.size(); i++) {
      if (children.get(i).compareTo(ln) > 0 && !children.contains(ln)) {
        children.add(i, ln);
        break;
      }
      // if the letter in the current lexicon node is stored is less than the
      // lexicon node letter that is currently there, break out of for loop
      // if (ln.getLetter() < children.get(i).getLetter()) {
      //   break;
      // }
    }
    if (!children.contains(ln)) {
      children.add(i, ln);
    }
  }

  /**
  * Method to get LexiconNode child for 'ch' out of children vector
  * @pre char ch is not empty and node is not null
  * @post appropriate LexiconNode corresponding to ch is returned
  * @param ch of type char representing letter stored in desired node
  * @return LexiconNode with ch stored as letter
  */
  public LexiconNode getChild(char ch) {
    for (int i = 0; i < children.size(); i++) {
      if (children.get(i).getLetter() == ch) {
        return children.get(i);
      }
    }
    return null;
  }

  /**
  * Method to remove LexiconNode child for 'ch' from children vector
  * @pre char ch is not empty and node is not null
  * @post appropriate LexiconNode corresponding to ch is removed from children vec
  * @param ch of type char representing letter stored in desired node
  */
  public void removeChild(char ch) {
    for (int i = 0; i < children.size(); i++) {
      if (children.get(i).getLetter() == ch) {
        children.remove(i);
      }
    }
  }

  /**
  * Method to iterate over children in alphabetical order
  * @post iterator object of lexicon node is returned
  * @return iterator object of LexiconNode
  */
  public Iterator<LexiconNode> iterator() {
    return children.iterator();
  }

  /**
  * Method to print a node and its children
  * @post return string
  * @return return string representing a node and its children
  */
  public String toString() {
    String ltr = "" + getLetter();
    String r = "";
    // traverse through children vec
    // recurisvely call toString and eventually adding all appropriate nodes to
    // string r
    for (int i = 0; i < children.size(); i++) {
      r += children.get(i).toString() + " ";
    }
    String tree = "(" + ltr + r + ")";
    return tree;
  }

  /**
   * Function to run LexiconNode as a script to test functionality of class
   */
  public static void main(String[] args) {
    // create vector of lexicon nodes children
    Vector<LexiconNode> children = new Vector<LexiconNode>();

    // create root node and 2 child nodes
    LexiconNode root = new LexiconNode('g', false);
    LexiconNode node1 = new LexiconNode('a', false);
    LexiconNode node2 = new LexiconNode('z', false);

    // add child nodes to the root
    root.addChild(node2);
    root.addChild(node1);

    //print out root and its children
    System.out.println(root);
  }
}
