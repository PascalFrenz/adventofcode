use std::collections::btree_map::BTreeMap;

// NOTE: All of the code is basically copied from
// * https://github.com/timvisee/advent-of-code-2021/tree/master/day12a
// * https://github.com/timvisee/advent-of-code-2021/tree/master/day12b
//
// I was just too stupid to solve this.

const EDGES: usize = 12;
pub fn task_a(input: &str) -> usize {
    let mut large_caves = vec![];
    let cave_indices: BTreeMap<&str, u8> = BTreeMap::from([("start", 1), ("end", 0)]);
    let mut cave_map: BTreeMap<u8, Vec<u8>> = BTreeMap::new();

    parse_input(input, &mut large_caves, cave_indices, &mut cave_map);

    let map = create_cave_map(large_caves, cave_map);

    let mut todo: Vec<(u8, u8, usize)> = vec![(0, 1, 1)];
    let (mut to, mut count) = ([1; EDGES], 0);
    while let Some((a, b, s)) = todo.pop() {
        to[b as usize] = a;
        count += map[&a]
            .iter()
            .enumerate()
            .filter(|&(_, routes)| *routes > 0)
            .fold(0, |acc, (c, _)| match c {
                1 => acc + s * map[&a][c],
                v => {
                    let visit = v != 0 && to[2..=b as usize].contains(&(v as u8));
                    (!visit).then(|| todo.push((v as u8, b + 1, s * map[&a][v])));
                    acc
                }
            });
    }

    count
}

pub fn task_b(input: &str) -> usize {
    const EDGES: usize = 12;
    let mut large_caves = vec![];
    let cave_indices: BTreeMap<&str, u8> = BTreeMap::from([("start", 1), ("end", 0)]);
    let mut cave_map: BTreeMap<u8, Vec<u8>> = BTreeMap::new();

    parse_input(input, &mut large_caves, cave_indices, &mut cave_map);

    let map = create_cave_map(large_caves, cave_map);

    let mut todo: Vec<(u8, u8, bool, usize)> = vec![(0, 1, true, 1)];
    let (mut to, mut count) = ([1; EDGES], 0);
    while let Some((a, b, t, s)) = todo.pop() {
        to[b as usize] = a;
        count += map[&a]
            .iter()
            .enumerate()
            .filter(|&(_, routes)| *routes > 0)
            .fold(0, |acc, (c, _)| match c {
                1 => acc + s * map[&a][c],
                v => {
                    let visit = v != 0 && to[2..=b as usize].contains(&(v as u8));
                    (t || !visit).then(|| todo.push((v as u8, b + 1, t && !visit, s * map[&a][v])));
                    acc
                }
            });
    }

    count
}

fn create_cave_map(large_caves: Vec<u8>, cave_map: BTreeMap<u8, Vec<u8>>) -> BTreeMap<u8, [usize; EDGES]> {
    let map = cave_map
        .keys()
        .filter(|any_cave| !large_caves.contains(any_cave))
        .map(|&small_cave| {
            (
                small_cave,
                // for all neighbours of small cave
                cave_map[&small_cave].iter().fold([0; EDGES], |mut chld, neighbour| {
                    if large_caves.contains(neighbour) {
                        cave_map[neighbour].iter().for_each(|cave| chld[*cave as usize] += 1);
                    } else {
                        chld[*neighbour as usize] += 1;
                    }
                    chld
                }),
            )
        })
        .collect::<BTreeMap<_, _>>();
    map
}

fn parse_input<'a>(input: &'a str, large_caves: &mut Vec<u8>, mut cave_indices: BTreeMap<&'a str, u8>, cave_map: &mut BTreeMap<u8, Vec<u8>>) {
    input.lines().for_each(|l| {
        // This indexing function takes a cave definition and assigns it a number.
        // Indices are deferred from the length of the already indexed caves.
        // A new cave simply is assigned the previous index + 1.
        // If a large cave was indexed, it is put into the uc-vector.
        let mut idx = |a| {
            let idx = cave_indices.len() as u8;
            *cave_indices.entry(a).or_insert_with(|| {
                let is_large_cave = a.as_bytes()[0] <= b'Z';
                (is_large_cave).then(|| large_caves.push(idx));
                idx
            })
        };

        // This creates the branches for the cave-map.
        // Given are the indices of the caves to insert. If b is given, it is inserted
        // into the "neighbour"-vector of a.
        let mut branch = |a, b| {
            let entry = cave_map.entry(a).or_insert_with(|| Vec::with_capacity(6));
            (b != 0).then(|| entry.push(b));
        };

        let (a, b) = l.split_once('-').unwrap();    // read caves as strings
        let (a, b) = (idx(a), idx(b));              // assign indices
        branch(a, b);                               // branch a -> b
        branch(b, a);                         // branch b -> a
    });
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_example_a() {
        let input = "start-A\nstart-b\nA-c\nA-b\nb-d\nA-end\nb-end";
        assert_eq!(10, task_a(input));
    }
}