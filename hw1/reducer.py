#!/usr/bin/python
import sys


def update_mean(chunk_size, mean, c_mean, c_size):
    return (chunk_size * mean + c_mean * c_size) / (chunk_size + c_size)

def update_var(chunk_size, mean, var, c_mean, c_size, c_var):
    part1 = (chunk_size * var + c_var * c_size) / (chunk_size + c_size)
    part2 = (c_mean - mean) / (chunk_size + c_size)
    part2 = part2 ** 2
    return chunk_size * c_size * part2 + part1


if __name__ == '__main__':
    current_size = 0
    running_mean = 0
    running_var = 0

    for line in sys.stdin:
        line = line.strip()
        chunk_size, mean, var = tuple(map(float, line.split(' ')))
        running_var = update_var(chunk_size, mean, var, running_mean, current_size, running_var)
        running_mean = update_mean(chunk_size, mean, running_mean, current_size)
        current_size += chunk_size

    print(running_mean, running_var)
