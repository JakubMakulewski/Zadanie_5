# Zadanie_5

Zmodyfikuj zadanie z poprzednich zajęć:

DONE
1) Do klasy Picture dodaj 3 metody, zwracające tekstową reprezentację Picture z obiektami posortowanymi według ustalonego porządku (wykorzystaj Arrays.sort i interfejs Comparator):
  - String toStringSortedByLabel() // posortowane po etykiekiecie, malejąco
  - String toStringSortedByClassName() // posortowane po nazwie klasy, rosnąco
  - String toStringSortedByDistanceFromOrigin() // posortowane wg. odległości punktu centroida obiektu  od początku układu współrzędnych.

DONE
2) Stwórz 2 intefejsy reprezentujace operacje, jakie można wykonać na danym obiekcie graficznym, dodaj ich implementację do wybranych klas:
  - Filllable z metodą fill(int color), implementowana przez wszystkie figury z polem (z wyjątkiem Point i Section),
  - Scalable z metodą scalePerimeter(double k), która liniowo skaluje obwód obiektu, zaimplementowana przez wybrane klasy.

DONE
3) Dodaj do klasy Picture metody fillObjects i scaleObjects,  która wykonuje operacje fill/scalePerimiter na obiektach posiadających odpowiedni interfejs (wykorzystaj operator instanceof).

4) Dodaj możliwość zapisu/odczytu obrazu z pliku za pomocą mechanizmu serializacji.
