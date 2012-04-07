// Key-size mismatch exception supporting KDTree class

package edu.wlu.cs.levy.CG;

class KeyMissingException extends Exception {

    public KeyMissingException() {
	super("Key not found");
    }
}
