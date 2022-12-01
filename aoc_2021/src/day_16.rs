use std::ops::Deref;
use bitvec::access::BitSafeU8;
use bitvec::bitvec;
use bitvec::field::BitField;
use bitvec::macros::internal::funty::Fundamental;
use bitvec::order::Msb0;
use bitvec::prelude::BitSlice;
use bitvec::vec::BitVec;
use bitvec::view::BitView;

pub fn task_a(input: &str) -> u32 {
    to_bits(input);
    return 0;
}

fn to_bits(input: &str) -> BitVec<u8, Msb0> {
    input.chars()
        .map(|c| c.to_digit(16).unwrap() as u8)
        .map(|num| num.view_bits()[4..8].to_bitvec())
        .reduce(|mut acc, mut it| {
            acc.append(&mut it);
            acc
        })
        .unwrap()
}

pub fn task_b(input: &str) -> u32 {
    return 0;
}

struct PacketHeader {
    version: u32,
    type_id: u32
}

type Packet = LiteralPacket;

struct LiteralPacket {
    header: PacketHeader,
    value: u32
}

fn parse_literal(mut bits: BitVec<u8, Msb0>) -> LiteralPacket {
    let (header_bits, value_bits) = bits.split_at_mut(6);
    let header = parse_header(header_bits.to_bitvec());
    let value = parse_literal_value(value_bits).load_be();

    LiteralPacket {
        header,
        value
    }
}

fn parse_literal_value(value_bits: &mut BitSlice<BitSafeU8, Msb0>) -> BitVec<u8, Msb0> {
    let (first, rest) = value_bits.split_first_mut().unwrap();
    let (partial, rest_value_bits) = rest.split_at_mut(4);
    let mut result = partial.to_bitvec();
    if *first.as_ref() {
        result.append(&mut parse_literal_value(rest_value_bits))
    } else if rest_value_bits.len() >= 5 {
        result.append(&mut rest_value_bits[0..5].to_bitvec())
    }
    result
}

fn parse_header(bits: BitVec<u8, Msb0>) -> PacketHeader {
    let version = bits[0..3].to_bitvec();
    let type_id= bits[3..6].to_bitvec();
    return PacketHeader {
        version: version.load_be(),
        type_id: type_id.load_be()
    };
}


#[cfg(test)]
mod tests {
    use bitvec::bits;
    use super::*;

    #[test]
    fn test_example_a() {
        let input = "";
        assert_eq!(10, task_a(input));
    }

    #[test]
    fn test_example_b() {
        let input = "";
        assert_eq!(10, task_b(input));
    }

    #[test]
    fn test_to_bits() {
        let expect = bits![u8, Msb0; 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0];
        assert_eq!(expect, to_bits("D2FE28"));
    }

    #[test]
    fn parse_packet_header() {
        let bits = to_bits("D2FE28");
        let actual = parse_header(bits);

        assert_eq!(6, actual.version);
        assert_eq!(4, actual.type_id);
    }

    #[test]
    fn parse_literal_packet() {
        let bits = to_bits("D2FE28");
        let packet = parse_literal(bits);

        assert_eq!(6, packet.header.version);
        assert_eq!(4, packet.header.type_id);
        assert_eq!(2021, packet.value);
    }
}
