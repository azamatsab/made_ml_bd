import pandas as pd
import numpy as np

DATA_PATH = 'data/prices.csv'


if __name__ == '__main__':
    data = pd.read_csv(DATA_PATH)
    prices = data['price']
    print('Mean:', np.mean(prices), 'Var:', np.var(prices))
