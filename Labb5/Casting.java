import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Casting {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // Läs in antal roller, scener och skådespelare
        int numOfRoles = Integer.parseInt(bufferedReader.readLine());
        int numOfScenes = Integer.parseInt(bufferedReader.readLine());
        int numOfActors = Integer.parseInt(bufferedReader.readLine());

        // Läs in roller och scener
        List<List<Integer>> roleToActorsMapping = readInput(bufferedReader, numOfRoles);
        List<List<Integer>> sceneToRolesMapping = readInput(bufferedReader, numOfScenes);

        // Skapa en lista för att räkna roller per skådespelare
        int[] RoleCount = new int[numOfActors];
        List<List<Integer>> actorRolesAllocation = new ArrayList<>();
        for (int i = 0; i < numOfRoles * 2 + 1; i++) {
            actorRolesAllocation.add(new ArrayList<>());
        }
        Set<Integer> assignedRoles = new HashSet<>();

        // Räkna antalet roller per skådespelare
        for (List<Integer> role : roleToActorsMapping) {
            for (int actor : role) {
                RoleCount[actor - 1]++;
            }
        }

        int primaryDivaCount = RoleCount[0];
        int secondaryDivaCount = RoleCount[1];
        RoleCount = Arrays.copyOfRange(RoleCount, 2, RoleCount.length);

        int primaryDiva = 1;
        int secondaryDiva = 2;
        if (primaryDivaCount < secondaryDivaCount) {
            primaryDiva = 2;
            secondaryDiva = 1;
        }

        // Allokera roller till divorna
        Set<Integer> invalidRolesForDivas = new HashSet<>();
        boolean roleFound = false;
        for (int i = 1; i < numOfRoles; i++) {
            if (roleToActorsMapping.get(i).contains(primaryDiva)) {
                for (int j = 0; j < numOfScenes; j++) {
                    if (sceneToRolesMapping.get(j).contains(i + 1)) {
                        invalidRolesForDivas.addAll(sceneToRolesMapping.get(j));
                    }
                }
                for (int roleIndex = 0; roleIndex < numOfRoles; roleIndex++) {
                    if (!invalidRolesForDivas.contains(roleIndex + 1) && roleToActorsMapping.get(roleIndex).contains(secondaryDiva)) {
                        actorRolesAllocation.get(secondaryDiva - 1).add(roleIndex + 1);
                        assignedRoles.add(roleIndex + 1);
                        actorRolesAllocation.get(primaryDiva - 1).add(i + 1);
                        assignedRoles.add(i + 1);
                        roleFound = true;
                        break;
                    }
                }
                if (assignedRoles.isEmpty()) {
                    invalidRolesForDivas.clear();
                }
                if (roleFound) {
                    break;
                }
            }
        }

        // Allokera fler roller till divorna
        for (int i = 0; i < numOfScenes; i++) {
            if (sceneToRolesMapping.get(i).contains(actorRolesAllocation.get(0).get(0)) || sceneToRolesMapping.get(i).contains(actorRolesAllocation.get(1).get(0))) {
                invalidRolesForDivas.addAll(sceneToRolesMapping.get(i));
            }
        }

        for (int diva = 1; diva <= 2; diva++) {
            for (int i = 0; i < numOfRoles; i++) {
                if (!invalidRolesForDivas.contains(i + 1) && roleToActorsMapping.get(i).contains(diva)) {
                    actorRolesAllocation.get(diva - 1).add(i + 1);
                    assignedRoles.add(i + 1);
                    for (int j = 0; j < numOfScenes; j++) {
                        if (sceneToRolesMapping.get(j).contains(i + 1)) {
                            invalidRolesForDivas.addAll(sceneToRolesMapping.get(j));
                        }
                    }
                }
            }
        }

        // Allokera roller till övriga skådespelare
        for (int i = 0; i < RoleCount.length; i++) {
            Set<Integer> invalidRolesForActor = new HashSet<>();
            boolean actorRoleFound = false;
            int bestActor = findIndexOfMax(RoleCount) + 3;
            RoleCount[bestActor - 3] = 0;

            for (int roleIndex = 0; roleIndex < roleToActorsMapping.size(); roleIndex++) {
                if (roleToActorsMapping.get(roleIndex).contains(bestActor) && !assignedRoles.contains(roleIndex + 1)) {
                    actorRolesAllocation.get(bestActor - 1).add(roleIndex + 1);
                    assignedRoles.add(roleIndex + 1);
                    invalidRolesForActor.add(roleIndex + 1);
                    actorRoleFound = true;
                    break;
                }
            }

            if (actorRoleFound) {
                for (int sceneIndex = 0; sceneIndex < numOfScenes; sceneIndex++) {
                    if (sceneToRolesMapping.get(sceneIndex).contains(actorRolesAllocation.get(bestActor - 1).get(0))) {
                        invalidRolesForActor.addAll(sceneToRolesMapping.get(sceneIndex));
                    }
                }

                for (int roleIndex = 0; roleIndex < numOfRoles; roleIndex++) {
                    if (!invalidRolesForActor.contains(roleIndex + 1) && roleToActorsMapping.get(roleIndex).contains(bestActor) && !assignedRoles.contains(roleIndex + 1)) {
                        actorRolesAllocation.get(bestActor - 1).add(roleIndex + 1);
                        assignedRoles.add(roleIndex + 1);
                        for (int sceneIndex = 0; sceneIndex < numOfScenes; sceneIndex++) {
                            if (sceneToRolesMapping.get(sceneIndex).contains(roleIndex + 1)) {
                                invalidRolesForActor.addAll(sceneToRolesMapping.get(sceneIndex));
                            }
                        }
                    }
                }
            }
        }

        // Allokera roller till super skådespelare
        List<Integer> unassignedRoles = new ArrayList<>();
        for (int i = 0; i < numOfRoles; i++) {
            if (!assignedRoles.contains(i + 1)) {
                unassignedRoles.add(i + 1);
            }
        }

        for (int i = 0; i < unassignedRoles.size(); i++) {
            actorRolesAllocation.get(numOfActors + i).add(unassignedRoles.get(i));
        }

        // Skriv ut resultatet
        int actorCount = 0;
        for (List<Integer> roles : actorRolesAllocation) {
            if (!roles.isEmpty()) {
                actorCount++;
            }
        }
        System.out.println(actorCount);

        for (int i = 0; i < actorRolesAllocation.size(); i++) {
            if (!actorRolesAllocation.get(i).isEmpty()) {
                String allocationString = (i + 1) + " " + actorRolesAllocation.get(i).size() + " ";
                for (int role : actorRolesAllocation.get(i)) {
                    allocationString += role + " ";
                }
                System.out.println(allocationString.trim());
            }
        }
    }

    private static List<List<Integer>> readInput(BufferedReader bufferedReader, int numberOfEntries) throws IOException {
        List<List<Integer>> inputList = new ArrayList<>();
        for (int i = 0; i < numberOfEntries; i++) {
            String[] input = bufferedReader.readLine().split(" ");
            List<Integer> row = new ArrayList<>();
            for (int j = 1; j < input.length; j++) {
                row.add(Integer.parseInt(input[j]));
            }
            inputList.add(row);
        }
        return inputList;
    }

    private static int findIndexOfMax(int[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
