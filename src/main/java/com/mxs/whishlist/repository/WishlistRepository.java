package com.mxs.whishlist.repository;

import com.mxs.whishlist.model.WishlistModel;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Class responsible for the wishlist domain repository pattern.
 */
@Repository
public interface WishlistRepository extends CassandraRepository<WishlistModel, UUID> {
    List<WishlistModel> findByUser(UUID user);
}
