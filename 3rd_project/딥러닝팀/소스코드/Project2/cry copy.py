import matplotlib.pyplot as plt
import IPython.display as ipd
import librosa
import librosa.display
import pandas as pd
import os
import librosa
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, Activation, Flatten
from tensorflow.keras.optimizers import Adam
from sklearn import metrics
from sklearn.model_selection import train_test_split
from tensorflow.keras.callbacks import ModelCheckpoint
from datetime import datetime
import pandas as pd
import numpy as np
from tqdm import tqdm

config= tf.compat.v1.ConfigProto()
config.gpu_options.allow_growth=True
sess= tf.compat.v1.Session(config=config)

audio_dataset_path ='Class'
matadata=pd.read_csv('Class/result.csv')



def features_extractor(file):
    audio, sample_rate = librosa.load(file_name, res_type = 'kaiser_fast')
    mfccs_features = librosa.feature.mfcc(y=audio, sr=sample_rate, n_mfcc=40)
    mfccs_scaled_features = np.mean(mfccs_features.T, axis=0)
    
    return mfccs_scaled_features

extracted_features = []
for index_num, row in tqdm(matadata.iterrows()):
    file_name = os.path.join(os.path.abspath(audio_dataset_path), 'fold' + str(row["fold"])+'\\', str(row["slice_file_name"]))
    final_class_labels = row["class"]
    data = features_extractor(file_name)
    extracted_features.append([data,final_class_labels])
extracted_features_df = pd.DataFrame(extracted_features, columns=['feature', 'class'])

X = np.array(extracted_features_df['feature'].tolist())
y = np.array(extracted_features_df['class'].tolist())
y = np.array(pd.get_dummies(y))

print(X.shape)
print(np.array(extracted_features_df['feature'].shape))

X_train, X_test, y_train, y_test = train_test_split(X,y,test_size= 0.2, random_state=0)

num_labels = y.shape[1]

model = Sequential()
model.add(Dense(100, input_shape =(40,)))
model.add(Activation('relu'))
model.add(Dropout(0.5))

model.add(Dense(200))
model.add(Activation('relu'))
model.add(Dropout(0.5))

model.add(Dense(100))
model.add(Activation('relu'))
model.add(Dropout(0.5))

model.add(Dense(num_labels))
model.add(Activation('softmax'))

model.compile(loss='categorical_crossentropy', metrics=['accuracy'], optimizer='adam')

num_epochs = 200
num_batch_size = 32

checkpointer = ModelCheckpoint(filepath='save_models/audio_classification.hdf5', verbose=1, save_best_only=True)
start = datetime.now

model.fit(X_train, y_train, batch_size=num_batch_size, epochs=num_epochs, validation_data=(X_test, y_test), callbacks=[checkpointer])


file_name='Class/1-22694-A.wav'
prediction_feature = features_extractor(file_name)
prediction_feature  = prediction_feature.reshape(1,-1)
print(model.predict_classes(prediction_feature))


