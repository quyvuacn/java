package org.aptech.t2109e.jspservlet.utils.select;

import lombok.Data;
import lombok.Getter;

import java.util.List;


@Getter
@Data
public class Pagination<T> extends SelectQueryBuilder {
    private int page;
    private int totalItems;
    private int totalPages;
    private List<T> items;

    public Pagination(){
        super();
    }
    public SelectQueryBuilder of(int page, int limit) {
        this.page = page;

        int offset = (page - 1) * limit;

        this.limit(limit);
        this.offset(offset);

        return this;
    }

    public void build(int totalItems, List<T> items) {
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / getLimit());
        this.items = items;
    }

    public boolean hasPreviousPage() {
        return page > 1;
    }

    public boolean hasNextPage() {
        return page < totalPages;
    }

}
