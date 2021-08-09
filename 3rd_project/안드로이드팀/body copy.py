from flask import Flask, render_template, Response
import cv2
import mediapipe as mp
import numpy as np
mp_drawing = mp.solutions.drawing_utils
mp_pose = mp.solutions.pose

counter = 0 
stage = None
time = 0

left_elbow_list = []
right_elbow_list = []
left_knee_list = []
right_knee_list = []

def calculate_angle(a,b,c):
    a = np.array(a) # First
    b = np.array(b) # Mid
    c = np.array(c) # End
    
    radians = np.arctan2(c[1]-b[1], c[0]-b[0]) - np.arctan2(a[1]-b[1], a[0]-b[0])
    angle = np.abs(radians*180.0/np.pi)
    
    if angle >180.0:
        angle = 360-angle
        
    return angle 

app = Flask(__name__)
cap = cv2.VideoCapture("0")

def gen_frames():  # generate frame by frame from camera
    with mp_pose.Pose(min_detection_confidence=0.5, min_tracking_confidence=0.5) as pose:
        while True:
            success, frame = cap.read()  # read the camera frame
    
            image = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            image.flags.writeable = False
        

            results = pose.process(image)
        

            image.flags.writeable = True
            image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
            

            try:
                landmarks = results.pose_landmarks.landmark
                
                left_shoulder = [landmarks[mp_pose.PoseLandmark.LEFT_SHOULDER.value].x,landmarks[mp_pose.PoseLandmark.LEFT_SHOULDER.value].y]
                left_elbow = [landmarks[mp_pose.PoseLandmark.LEFT_ELBOW.value].x,landmarks[mp_pose.PoseLandmark.LEFT_ELBOW.value].y]
                left_wrist = [landmarks[mp_pose.PoseLandmark.LEFT_WRIST.value].x,landmarks[mp_pose.PoseLandmark.LEFT_WRIST.value].y]
                left_elbow_angle = calculate_angle(left_shoulder, left_elbow, left_wrist)
                    
                right_shoulder = [landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].x,landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].y]
                right_elbow = [landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].x,landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].y]
                right_wrist = [landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].x,landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].y]
                right_elbow_angle = calculate_angle(right_shoulder, right_elbow, right_wrist)
                
                right_hip = [landmarks[mp_pose.PoseLandmark.RIGHT_HIP.value].x,landmarks[mp_pose.PoseLandmark.RIGHT_HIP.value].y]
                right_knee = [landmarks[mp_pose.PoseLandmark.RIGHT_KNEE.value].x,landmarks[mp_pose.PoseLandmark.RIGHT_KNEE.value].y]
                right_ankle = [landmarks[mp_pose.PoseLandmark.RIGHT_ANKLE.value].x,landmarks[mp_pose.PoseLandmark.RIGHT_ANKLE.value].y]
                right_knee_angle = calculate_angle(right_hip, right_knee, right_ankle)            

                left_hip = [landmarks[mp_pose.PoseLandmark.LEFT_HIP.value].x,landmarks[mp_pose.PoseLandmark.LEFT_HIP.value].y]
                left_knee = [landmarks[mp_pose.PoseLandmark.LEFT_KNEE.value].x,landmarks[mp_pose.PoseLandmark.LEFT_KNEE.value].y]
                left_ankle = [landmarks[mp_pose.PoseLandmark.LEFT_ANKLE.value].x,landmarks[mp_pose.PoseLandmark.LEFT_ANKLE.value].y]
                left_knee_angle = calculate_angle(left_hip, left_knee, left_ankle)    
                

                cv2.putText(image, str(left_elbow_angle), 
                            tuple(np.multiply(left_elbow, [300, 700]).astype(int)), 
                            cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2, cv2.LINE_AA
                                    )
                cv2.putText(image, str(right_elbow_angle), 
                            tuple(np.multiply(right_elbow, [300, 700]).astype(int)), 
                            cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2, cv2.LINE_AA
                                    )
                cv2.putText(image, str(right_knee_angle), 
                            tuple(np.multiply(right_knee, [300, 700]).astype(int)), 
                            cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2, cv2.LINE_AA
                                    )
                cv2.putText(image, str(left_knee_angle), 
                            tuple(np.multiply(left_knee, [300, 700]).astype(int)), 
                            cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2, cv2.LINE_AA
                                    )
                
                
                if(time<200):
                    time = time +1
                elif(time>=200):
                    left_elbow_list.append(left_elbow_angle)
                    right_elbow_list.append(right_elbow_angle)
                    left_knee_list.append(left_knee_angle)
                    right_knee_list.append(right_knee_angle)
                    time = 0
                    

                
                

    #             if angle > 160:
    #                 stage = "down"
    #             if angle < 30 and stage =='down':
    #                 stage="up"
    #                 counter +=1
                        
            except:
                pass
            

    #         cv2.rectangle(image, (0,0), (225,73), (245,117,16), -1)
            

    #         cv2.putText(image, 'REPS', (15,12), 
    #                     cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,0), 1, cv2.LINE_AA)
    #         cv2.putText(image, str(counter), 
    #                     (10,60), 
    #                     cv2.FONT_HERSHEY_SIMPLEX, 2, (255,255,255), 2, cv2.LINE_AA)
            

    #         cv2.putText(image, 'STAGE', (65,12), 
    #                     cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,0), 1, cv2.LINE_AA)
    #         cv2.putText(image, stage, 
    #                     (60,60), 
    #                     cv2.FONT_HERSHEY_SIMPLEX, 2, (255,255,255), 2, cv2.LINE_AA)
            
            

            mp_drawing.draw_landmarks(image, results.pose_landmarks, mp_pose.POSE_CONNECTIONS,
                                    mp_drawing.DrawingSpec(color=(245,117,66), thickness=2, circle_radius=2), 
                                    mp_drawing.DrawingSpec(color=(245,66,230), thickness=2, circle_radius=2) 
                                    )               
            

            ret, buffer = cv2.imencode('.jpg', image)

            frame = buffer.tobytes()

            yield (b'--frame\r\n'

                    b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

                    # concat frame one by one and show result
@app.route('/video_feed')

def video_feed():

    """Video streaming route. Put this in the src attribute of an img tag."""

    return Response(gen_frames(),

                    mimetype='multipart/x-mixed-replace; boundary=frame')





@app.route('/')

def index():

    """Video streaming home page."""

    return render_template('index.html')





if (__name__ == '__main__'):

    app.run(host='0.0.0.0', port=5000)