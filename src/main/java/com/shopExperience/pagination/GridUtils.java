package com.shopExperience.pagination;

import java.util.ArrayList;
import java.util.List;

public class GridUtils {

  public static int getTotalNumberOfPages(List<?> dataSet, int rowsPerPage){
    int totalNumberOfPages = (int) Math.ceil((double)dataSet.size() / (double)rowsPerPage);
    return totalNumberOfPages;
  }

  public static int getCurrentPageNumber(List<?> dataSet, int pageNumber, int rowsPerPage){
    return pageNumber++;
  }

  public static <T> List<T> getDataForPage(List<T> dataSet, int pageNumber, int itemsPerPage){
    List<T> result = null;

    int fromIndex = 0;
    if(pageNumber > 1) {
      fromIndex = (pageNumber - 1) * itemsPerPage;
    }

    if(fromIndex >= dataSet.size()){
      result = new ArrayList<T>();
    }

    int endIndex = fromIndex + itemsPerPage;
    if(endIndex > dataSet.size()){
      result = dataSet.subList(fromIndex, dataSet.size());
    }
    else {
      result = dataSet.subList(fromIndex, endIndex);
    }

    return result;
  }
}

