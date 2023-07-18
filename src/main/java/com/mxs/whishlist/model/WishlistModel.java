package com.mxs.whishlist.model;

import com.mxs.whishlist.type.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

/**
 * Class responsible for representing the wishlist domain.
 */
@Data
@AllArgsConstructor
@Table("wishlist")
public class WishlistModel {

    @PrimaryKeyColumn(name = "user", ordinal = 0, type = PARTITIONED)
    private UUID user;
    @PrimaryKeyColumn(name = "category", ordinal = 1, type = CLUSTERED)
    @CassandraType(type = CassandraType.Name.VARCHAR)
    private CategoryType category;
    @PrimaryKeyColumn(name = "creation", ordinal = 2, type = CLUSTERED, ordering = DESCENDING)
    private LocalDateTime creation;
    private LocalDateTime removal;
    @PrimaryKeyColumn(name = "product", ordinal = 3, type = CLUSTERED)
    private UUID product;
    private String link;
    private boolean status;

    public WishlistModel() {
        status = true;
        creation = LocalDateTime.now();
    }

    public void inactivateRecord() {
        status = false;
        removal = LocalDateTime.now();
    }
}