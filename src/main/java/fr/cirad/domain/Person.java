package fr.cirad.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Person implements Comparable<Person> {

    public String name;

    public PersonType personType;

    public List<Skill> skills = new ArrayList<>();

    public Location location;

    public List<TimeSlot> availability = new ArrayList<>();

    public List<Skill> requiredSkills = new ArrayList<>();

    public Boolean needsEvaluation;

    public List<Person> vetoes = new ArrayList<>();

    public List<List<String>> hasAlreadyInspected = new ArrayList<>();

    public Long maxNumberOfInspections;

    public Settings settings;

    @JsonIgnore
    public Range numberOfAssignmentsRangeConstraint = new Range(0, 5);

    @JsonIgnore
    public Range travellingDistanceRangeConstraint = new Range(0, 100);

    private static final Comparator<Person> COMPARATOR = Comparator.comparing(p -> p.name);

    public Person() {
        // must have a no-args constructor so it can be constructed by OptaPlanner
    }

    public Person(String name, Settings settings) {
        this.name = name;
        this.settings = settings;
    }

    /**
     * This function must be called on each Person before running the solver. It will set the
     * constraints on the number of assignments.
     *
     * @param settings The settings object that was passed to the plugin.
     */
    public void init(Settings settings) {
        this.settings = settings;
    }

    // Checks if the person has one of the skills
    public boolean hasSkill(Skill skill) {
        return this.skills.contains(skill);
    }

    // Checks if a person is available for a given time slot
    public boolean isAvailable(TimeSlot t) {
        return availability.contains(t);
    }

    // Checks if two persons are on veto each other
    public boolean isVetoed(Person other) {
        return vetoes.contains(other) || other.vetoes.contains(this);
    }

    /**
     * If the list of people I've already inspected is not empty and has more than one element, then
     * for each element in the list, if the element contains the name of the person I'm evaluating,
     * then return true
     *
     * @param evaluatedPerson The person that is being evaluated.
     * @return A boolean value.
     */
    public boolean hasAlreadyInspectedInThePast(Person evaluatedPerson) {
        if (hasAlreadyInspected != null && hasAlreadyInspected.size() > 1) {
            for (int i = 1; i < hasAlreadyInspected.size(); i++) {
                var current = hasAlreadyInspected.get(i);
                if (current.contains(evaluatedPerson.name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAlreadyInspectedLastTime(Person evaluatedPerson) {
        return (hasAlreadyInspected != null && !hasAlreadyInspected.isEmpty()
                && hasAlreadyInspected.get(0).contains(evaluatedPerson.name));
    }

    /**
     * "If the sum of the distances of all assignments is not in the range of the travelling
     * distance constraint, then the committee is not travelling in range."
     *
     * @return A boolean value.
     */
    public boolean isNotTravellingInRange(int distance) {
        return !travellingDistanceRangeConstraint.contains(distance);
    }

    @Override
    public int compareTo(Person o) {
        return COMPARATOR.compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person)) {
            return false;
        }
        Person other = (Person) o;
        return this.name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return "Person: " + name;
    }

}
