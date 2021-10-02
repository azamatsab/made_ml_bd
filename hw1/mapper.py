#!/usr/bin/python
import sys


def mean(data):
    return sum(data) / len(data)

def var(data, mean_):
    running_var = 0
    for elem in data:
        running_var += (elem - mean_) ** 2
    return running_var / len(data)

if __name__ == '__main__':
    prices = []
    for line in sys.stdin:
        line = line.strip()
        price = line.split(',')[-1]
        try:
            price = float(price)
        except Exception as err:
            continue
        prices.append(price)

    d_mean = mean(prices)
    d_var = var(prices, d_mean)
    print(len(prices), d_mean, d_var)
