public class Temp {
    public static void main(String[] args) {
        int[] arr = {7, 10, 5, 3, 8, 4, 2, 9, 6};

        // 선택 정렬 구현
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;  // 최솟값의 위치를 저장

            // i 이후의 원소들 중 최솟값 찾기
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // 현재 위치(i)와 찾은 최솟값의 위치를 교환
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }

        // 정렬된 배열 출력
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}