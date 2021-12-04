package ru.otus;

import java.util.OptionalInt;

public class Main {

  public static void main(String[] args) {
    int[] arr = { 1, 2, 3, 1, 4, 4, 4 };
    int[] arr2 = { 1, 2, 3, 4 };
    // TODO: вывести ровно один повторяющийся элемент
    OptionalInt result = findDubInArray(arr);
    result.ifPresentOrElse(r -> System.out.println("Элемент: " + r),
                           () -> System.out.println("Нет повторяющихся элементов"));
  }

  private static boolean compareArrayElementWithAnother(int indexOfElement, int[] arr) {
    for (int j = indexOfElement + 1; j < arr.length; j++) {
      if (arr[indexOfElement] == arr[j]) {
        return true;
      }
    }
    return false;
  }

  private static OptionalInt findDubInArray(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      if (compareArrayElementWithAnother(i, arr)) {
        return OptionalInt.of(arr[i]);
      }
    }
    return OptionalInt.empty();
  }
}
