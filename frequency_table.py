import pandas as pd

data = pd.read_csv("predictive_dataset.csv")

freq_table = data.value_counts().reset_index(name="Frequency")

print(freq_table)