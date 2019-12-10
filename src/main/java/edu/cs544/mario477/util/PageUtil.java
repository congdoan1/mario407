package edu.cs544.mario477.util;

import edu.cs544.mario477.common.Constants;
import edu.cs544.mario477.common.PageDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

    public static Pageable initPage(PageDTO pageDTO) {
        return PageRequest.of(getPageDetail(pageDTO)[0], getPageDetail(pageDTO)[1]);
    }

    public static Pageable initPage(PageDTO pageDTO, Sort sort) {
        return PageRequest.of(getPageDetail(pageDTO)[0], getPageDetail(pageDTO)[1], sort);
    }

    private static int[] getPageDetail(PageDTO pageDTO) {
        Integer page = pageDTO.getPage();
        Integer size = pageDTO.getSize();

        if (page == null || page < 1) {
            page = Constants.DEFAULT_PAGE;
        }
        if (size == null || size < 1) {
            size = Constants.DEFAULT_SIZE;
        }
        return new int[] {page, size};
    }
}
