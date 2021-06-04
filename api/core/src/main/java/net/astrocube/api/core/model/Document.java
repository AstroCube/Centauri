package net.astrocube.api.core.model;

/**
 * Base interface for serializable API documents, including documents stored
 * in the database ({@link Model} and {@link PartialModel}s) and directives
 * exchanged through the API ({@link net.astrocube.api.core.message.Message}s).
 * <p>
 * {@link com.fasterxml.jackson.databind.ObjectMapper} is responsible for serializing and deserializing
 * Registration happens on demand and is entirely automatic.
 */
public interface Document {
}
