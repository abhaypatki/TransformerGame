/**
 * 
 */
package com.aequilibrium.transformers.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aequilibrium.transformers.data.Transformer;

/**
 * @author Abhay
 * The interface for Transformer Repository
 */
@Repository
public interface TransformerRepository extends CrudRepository<Transformer, Integer>{
	
}
