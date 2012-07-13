/*
 * Copyright (C) 2011 Steven Luo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package evervolv.androidterm;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import evervolv.androidterm.model.UpdateCallback;
import evervolv.androidterm.session.TermSession;
import evervolv.androidterm.util.SessionList;

public class WindowListAdapter extends BaseAdapter implements UpdateCallback {
    private SessionList mSessions;

    public WindowListAdapter(SessionList sessions) {
        setSessions(sessions);
    }

    public void setSessions(SessionList sessions) {
        mSessions = sessions;
        if (sessions != null) {
            onUpdate();
        }
    }

    public int getCount() {
        return mSessions.size();
    }

    public Object getItem(int position) {
        return mSessions.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Activity act = (Activity) parent.getContext();
        View child = act.getLayoutInflater().inflate(R.layout.window_list_item, parent, false);
        TextView label = (TextView) child.findViewById(R.id.window_list_label);
        label.setText(act.getString(R.string.window_title, position + 1));

        View close = child.findViewById(R.id.window_list_close);
        final SessionList sessions = mSessions;
        final int closePosition = position;
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TermSession session = sessions.remove(closePosition);
                if (session != null) {
                    session.finish();
                    notifyDataSetChanged();
                }
            }
        });

        return child;
    }

    public void onUpdate() {
        notifyDataSetChanged();
    }
}