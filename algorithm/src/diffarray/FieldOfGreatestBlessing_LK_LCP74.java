package diffarray;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * LCP 74. 最强祝福力场
 * 小扣在探索丛林的过程中，无意间发现了传说中“落寞的黄金之都”。而在这片建筑废墟的地带中，小扣使用探测仪监测到了存在某种带有「祝福」效果的力场。
 * 经过不断的勘测记录，小扣将所有力场的分布都记录了下来。forceField[i] = [x,y,side] 表示第 i 片力场将覆盖以坐标 (x,y) 为中心，
 * 边长为 side 的正方形区域。若任意一点的 力场强度 等于覆盖该点的力场数量，请求出在这片地带中 力场强度 最强处的 力场强度。
 * 注意：
 * 力场范围的边缘同样被力场覆盖。
 *
 * 提示：
 * 1 <= forceField.length <= 100
 * forceField[i].length == 3
 * 0 <= forceField[i][0], forceField[i][1] <= 10^9
 * 1 <= forceField[i][2] <= 10^9
 */
public class FieldOfGreatestBlessing_LK_LCP74 {
    public static int fieldOfGreatestBlessing(int[][] forceField) {
        // 离散处理x,y坐标，将坐标处理成无小数位置的
        Set<Long> setX = new TreeSet<>();
        Set<Long> setY = new TreeSet<>();
        for (int[] force : forceField) {
            setX.add(force[0] * 2L - force[2]);
            setX.add(force[0] * 2L + force[2]);
            setY.add(force[1] * 2L - force[2]);
            setY.add(force[1] * 2L + force[2]);
        }
        // 离散后的坐标值可能会很大，将坐标值对应成个数值
        Map<Long, Integer> mapX = new HashMap<>();
        Map<Long, Integer> mapY = new HashMap<>();
        int n = 1;
        for (long x : setX) {
            mapX.put(x, n++);
        }
        n = 1;
        for (long y : setY) {
            mapY.put(y, n++);
        }
        n = Math.max(mapX.size(), mapY.size());
        int[][] diff = new int[n + 2][n + 2];
        for (int i = 0, a, b, c, d; i < forceField.length; i++) {
            a = mapX.get(forceField[i][0] * 2L - forceField[i][2]);
            b = mapY.get(forceField[i][1] * 2L - forceField[i][2]);
            c = mapX.get(forceField[i][0] * 2L + forceField[i][2]);
            d = mapY.get(forceField[i][1] * 2L + forceField[i][2]);
            add(diff, a, b, c, d);
        }
        int ans = 0;
        for (int i = 1; i < diff.length; i++) {
            for (int j = 1; j < diff[0].length; j++) {
                diff[i][j] += diff[i - 1][j] + diff[i][j - 1] - diff[i - 1][j - 1];
                ans = Math.max(ans, diff[i][j]);
            }
        }
        return ans;
    }
    
    public static void add(int[][] diff, int a, int b, int c, int d) {
        diff[a][b] += 1;
        diff[a][d + 1] -= 1;
        diff[c + 1][b] -= 1;
        diff[c + 1][d + 1] += 1;
    }
}
